
package com.example.businessconnect

import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.MapTileIndex
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.io.File

// South Africa centre - the map starts here when there is no pin yet
private const val DEFAULT_LAT = -28.5
private const val DEFAULT_LON = 24.7

/**
 * Esri World Street Map.
 *
 * Uses Esri's public tile service.
 * No API key is required for this endpoint.
 */
private val EsriStreetTiles = object : OnlineTileSourceBase(
    "EsriWorldStreet",
    0,
    19,
    256,
    "",
    arrayOf(
        "https://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/"
    ),
    "Sources: Esri, DeLorme, HERE, USGS, Intermap, iPC, NRCAN, Esri Japan, METI, Esri China (Hong Kong), Esri (Thailand), MapmyIndia, TomTom"
) {
    override fun getTileURLString(pMapTileIndex: Long): String =
        baseUrl +
                MapTileIndex.getZoom(pMapTileIndex) + "/" +
                MapTileIndex.getY(pMapTileIndex) + "/" +
                MapTileIndex.getX(pMapTileIndex)
}

/**
 * osmdroid needs a user agent.
 * Tile cache is stored in app storage.
 */
fun initOsmdroid(context: Context) {
    val config = Configuration.getInstance()
    config.userAgentValue = "BusinessConnect/1.0"

    val base = File(context.cacheDir, "osmdroid")
    config.osmdroidBasePath = base
    config.osmdroidTileCache = File(base, "tiles")
}

private tailrec fun Context.findLifecycleOwner(): LifecycleOwner? =
    when (this) {
        is LifecycleOwner -> this
        is ContextWrapper -> baseContext.findLifecycleOwner()
        else -> null
    }

/** Creates a MapView wired to the Activity lifecycle. */
@Composable
private fun rememberMapView(): MapView {
    val context = LocalContext.current

    val mapView = remember {
        initOsmdroid(context)

        MapView(context).apply {
            setTileSource(EsriStreetTiles)
            setMultiTouchControls(true)

            zoomController.setVisibility(
                CustomZoomButtonsController.Visibility.NEVER
            )

            minZoomLevel = 3.0
            controller.setZoom(5.5)
            controller.setCenter(
                GeoPoint(DEFAULT_LAT, DEFAULT_LON)
            )

            // Prevent the parent scrolling screen from stealing map gestures.
            setOnTouchListener { v, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE ->
                        v.parent?.requestDisallowInterceptTouchEvent(true)

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL ->
                        v.parent?.requestDisallowInterceptTouchEvent(false)
                }
                false
            }
        }
    }

    val owner = context.findLifecycleOwner()

    DisposableEffect(owner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }

        owner?.lifecycle?.addObserver(observer)

        onDispose {
            owner?.lifecycle?.removeObserver(observer)
            mapView.onPause()
            mapView.onDetach()
        }
    }

    return mapView
}

private class PinState {
    var zoomed = false
}

/**
 * Map with a single pin.
 *
 * editable = true:
 * Tap the map to place a pin, or drag an existing pin.
 *
 * editable = false:
 * Read-only preview.
 */
@Composable
fun LocationMapView(
    latitude: Double?,
    longitude: Double?,
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    onPinMoved: (Double, Double) -> Unit = { _, _ -> }
) {
    val mapView = rememberMapView()
    val currentOnPinMoved by rememberUpdatedState(onPinMoved)
    val pinState = remember { PinState() }

    val marker = remember(mapView) {
        Marker(mapView).apply {
            setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_BOTTOM
            )

            isDraggable = editable

            setOnMarkerClickListener { _, _ -> true }

            setOnMarkerDragListener(
                object : Marker.OnMarkerDragListener {
                    override fun onMarkerDrag(marker: Marker?) {}

                    override fun onMarkerDragStart(marker: Marker?) {}

                    override fun onMarkerDragEnd(marker: Marker?) {
                        marker?.position?.let {
                            currentOnPinMoved(
                                it.latitude,
                                it.longitude
                            )
                        }
                    }
                }
            )
        }
    }

    remember(mapView, editable) {
        if (editable) {
            mapView.overlays.add(
                0,
                MapEventsOverlay(
                    object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(
                            p: GeoPoint?
                        ): Boolean {
                            p?.let {
                                currentOnPinMoved(
                                    it.latitude,
                                    it.longitude
                                )
                            }
                            return true
                        }

                        override fun longPressHelper(
                            p: GeoPoint?
                        ): Boolean = false
                    }
                )
            )
        }
        true
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier,
        update = { map ->
            if (latitude != null && longitude != null) {
                val point = GeoPoint(latitude, longitude)

                marker.position = point

                if (!map.overlays.contains(marker)) {
                    map.overlays.add(marker)
                }

                if (!pinState.zoomed) {
                    map.controller.setZoom(16.0)
                    map.controller.setCenter(point)
                    pinState.zoomed = true
                } else if (!map.boundingBox.contains(point)) {
                    map.controller.animateTo(point)
                }
            } else {
                map.overlays.remove(marker)
                pinState.zoomed = false
            }

            map.invalidate()
        }
    )
}

/**
 * Editable map that also looks up the address
 * for wherever the pin lands.
 */
@Composable
fun LocationMapPicker(
    latitude: String,
    longitude: String,
    onPinMoved: (
        latitude: Double,
        longitude: Double,
        address: LocationSearchResult?
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    LocationMapView(
        latitude = latitude.toDoubleOrNull(),
        longitude = longitude.toDoubleOrNull(),
        modifier = modifier,
        editable = true,
        onPinMoved = { lat, lon ->
            onPinMoved(lat, lon, null)

            scope.launch {
                val address = withContext(Dispatchers.IO) {
                    LocationSearchService.reverse(lat, lon)
                }

                if (address != null) {
                    onPinMoved(lat, lon, address)
                }
            }
        }
    )
}

/**
 * Use my current location button.
 * Requests permission, obtains GPS coordinates,
 * then performs reverse address lookup.
 */
@Composable
fun UseMyLocationButton(
    onLocationFound: (
        latitude: Double,
        longitude: Double,
        address: LocationSearchResult?
    ) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    errorColor: Color = MaterialTheme.colorScheme.error
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    fun fetch() {
        scope.launch {
            loading = true
            message = ""

            val location = LocationUtils.getCurrentLocation(context)

            if (location == null) {
                message =
                    "Couldn't get your location. Check that GPS is on and try again."
            } else {
                val address = withContext(Dispatchers.IO) {
                    LocationSearchService.reverse(
                        location.latitude,
                        location.longitude
                    )
                }

                onLocationFound(
                    location.latitude,
                    location.longitude,
                    address
                )
            }

            loading = false
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.values.any { it }) {
            fetch()
        } else {
            message =
                "Location permission denied. You can still search or tap the map."
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = {
                if (LocationUtils.hasPermission(context)) {
                    fetch()
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = containerColor,
                disabledContentColor = contentColor
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    "LOCATING...",
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    "USE MY CURRENT LOCATION",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (message.isNotBlank()) {
            Text(
                text = message,
                color = errorColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

/** Small coloured dot used for map pins and legends. */
fun statusColorFor(status: String): Int =
    when (status) {
        "New Prospect" -> 0xFF00AEEF.toInt()
        "Existing Store" -> 0xFF35C759.toInt()
        else -> 0xFF9AA4AE.toInt()
    }

/** All businesses on one map, pins coloured by store status. */
@Composable
fun BusinessesMapView(
    businesses: List<BusinessEntity>,
    modifier: Modifier = Modifier,
    onBusinessTapped: (BusinessEntity) -> Unit
) {
    val mapView = rememberMapView()
    val context = LocalContext.current
    val currentOnTapped by rememberUpdatedState(onBusinessTapped)
    val fitState = remember { PinState() }

    AndroidView(
        factory = { mapView },
        modifier = modifier,
        update = { map ->
            map.overlays.removeAll { it is Marker }

            val density =
                context.resources.displayMetrics.density

            val sizePx = (22 * density).toInt()
            val points = mutableListOf<GeoPoint>()

            businesses.forEach { business ->
                val lat = business.latitude.toDoubleOrNull()
                val lon = business.longitude.toDoubleOrNull()

                if (lat == null || lon == null) return@forEach

                val point = GeoPoint(lat, lon)
                points += point

                val pin = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(statusColorFor(business.storeStatus))
                    setStroke(
                        (2 * density).toInt(),
                        0xFFFFFFFF.toInt()
                    )
                    setSize(sizePx, sizePx)
                }

                map.overlays.add(
                    Marker(map).apply {
                        position = point
                        icon = pin

                        setAnchor(
                            Marker.ANCHOR_CENTER,
                            Marker.ANCHOR_CENTER
                        )

                        setOnMarkerClickListener { _, _ ->
                            currentOnTapped(business)
                            true
                        }
                    }
                )
            }

            // Zoom to fit all pins the first time they appear.
            if (!fitState.zoomed && points.isNotEmpty()) {
                fitState.zoomed = true

                map.post {
                    if (points.size == 1) {
                        map.controller.setZoom(15.0)
                        map.controller.setCenter(points.first())
                    } else {
                        map.zoomToBoundingBox(
                            BoundingBox.fromGeoPoints(points),
                            false,
                            (80 * density).toInt()
                        )
                    }
                }
            }

            map.invalidate()
        }
    )
}
