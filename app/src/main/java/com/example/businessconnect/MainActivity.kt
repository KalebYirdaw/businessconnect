package com.example.businessconnect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.businessconnect.ui.theme.BusinessConnectTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            BusinessConnectTheme {

                BusinessConnectRoot()

            }
        }
    }
}


@Composable
private fun BusinessConnectRoot() {

    val context =
        LocalContext.current


    val database =
        remember {

            BusinessDatabase
                .getDatabase(context)

        }


    // ---------------------------------------------------------
    // BUSINESS REPOSITORY
    // ---------------------------------------------------------

    val businessRepository =
        remember {

            BusinessRepository(
                database.businessDao()
            )

        }


    // ---------------------------------------------------------
    // PRODUCT REPOSITORY
    // ---------------------------------------------------------

    val productRepository =
        remember {

            ProductRepository(
                database.productDao()
            )

        }


    // ---------------------------------------------------------
    // PRODUCT CATALOGUE REPOSITORY
    // ---------------------------------------------------------

    val productCatalogueRepository =
        remember {

            ProductCatalogueRepository(
                database.productCatalogueDao()
            )

        }


    // ---------------------------------------------------------
    // BUSINESS VIEWMODEL FACTORY
    // ---------------------------------------------------------

    val businessFactory =
        remember {

            BusinessViewModelFactory(
                businessRepository,
                productRepository
            )

        }


    // ---------------------------------------------------------
    // PRODUCT VIEWMODEL FACTORY
    // ---------------------------------------------------------

    val productFactory =
        remember {

            ProductViewModelFactory(
                productRepository
            )

        }


    // ---------------------------------------------------------
    // PRODUCT CATALOGUE VIEWMODEL FACTORY
    // ---------------------------------------------------------

    val productCatalogueFactory =
        remember {

            ProductCatalogueViewModelFactory(
                productCatalogueRepository
            )

        }


    // ---------------------------------------------------------
    // VIEWMODELS
    // ---------------------------------------------------------

    val businessViewModel:
            BusinessViewModel =
        viewModel(
            factory = businessFactory
        )


    val productViewModel:
            ProductViewModel =
        viewModel(
            factory = productFactory
        )


    val productCatalogueViewModel:
            ProductCatalogueViewModel =
        viewModel(
            factory =
                productCatalogueFactory
        )


    // ---------------------------------------------------------
    // START APP
    // ---------------------------------------------------------

    BusinessConnectApp(

        viewModel =
            businessViewModel,

        productViewModel =
            productViewModel,

        productCatalogueViewModel =
            productCatalogueViewModel
    )
}


@Composable
fun BusinessConnectApp(

    viewModel:
    BusinessViewModel,

    productViewModel:
    ProductViewModel,

    productCatalogueViewModel:
    ProductCatalogueViewModel

) {

    // ---------------------------------------------------------
    // SEED DEFAULT PRODUCT CATALOGUE
    // ---------------------------------------------------------

    LaunchedEffect(Unit) {

        productCatalogueViewModel
            .seedDefaultProducts()

    }


    // ---------------------------------------------------------
    // BUSINESS DATA
    // ---------------------------------------------------------

    var businessData by remember {

        mutableStateOf(
            BusinessData()
        )

    }


    // ---------------------------------------------------------
    // SELECTED BUSINESS
    // ---------------------------------------------------------

    var selectedBusiness by remember {

        mutableStateOf<BusinessEntity?>(
            null
        )

    }


    // ---------------------------------------------------------
    // CURRENT SCREEN
    // ---------------------------------------------------------

    var currentScreen by remember {

        mutableStateOf(
            "dashboard"
        )

    }


    // ---------------------------------------------------------
    // APP SCAFFOLD
    // ---------------------------------------------------------

    Scaffold(

        modifier =
            Modifier.fillMaxSize()

    ) { innerPadding ->


        when (currentScreen) {


            // =================================================
            // DASHBOARD
            // =================================================

            "dashboard" -> {

                DashboardScreen(

                    modifier =
                        Modifier.padding(
                            innerPadding
                        ),

                    onAddBusiness = {

                        businessData =
                            BusinessData()

                        currentScreen =
                            "addBusiness"

                    },

                    onViewBusinesses = {

                        currentScreen =
                            "businessList"

                    }
                )
            }


            // =================================================
            // ADD BUSINESS
            // =================================================

            "addBusiness" -> {

                AddBusinessScreen(

                    businessData =
                        businessData,

                    modifier =
                        Modifier.padding(
                            innerPadding
                        ),

                    onBack = {

                        currentScreen =
                            "dashboard"

                    },

                    onNext = {

                        currentScreen =
                            "ownerDetails"

                    }
                )
            }


            // =================================================
            // OWNER DETAILS
            // =================================================

            "ownerDetails" -> {

                OwnerDetailsScreen(

                    businessData =
                        businessData,

                    onBack = {

                        currentScreen =
                            "addBusiness"

                    },

                    onNext = {

                        currentScreen =
                            "locationDetails"

                    }
                )
            }


            // =================================================
            // LOCATION DETAILS
            // =================================================

            "locationDetails" -> {

                LocationDetailsScreen(

                    businessData =
                        businessData,

                    onBack = {

                        currentScreen =
                            "ownerDetails"

                    },

                    onNext = {

                        currentScreen =
                            "products"

                    }
                )
            }


            // =================================================
            // PRODUCT ASSESSMENT
            // =================================================

            "products" -> {

                ProductsScreen(

                    businessData =
                        businessData,

                    productCatalogueViewModel =
                        productCatalogueViewModel,

                    onBack = {

                        currentScreen =
                            "locationDetails"

                    },

                    onNext = {

                        currentScreen =
                            "review"

                    },

                    onAddProduct = {

                        currentScreen =
                            "addProduct"

                    }
                )
            }


            // =================================================
            // ADD NEW PRODUCT
            // =================================================

            "addProduct" -> {

                AddProductScreen(

                    productCatalogueViewModel =
                        productCatalogueViewModel,

                    onBack = {

                        currentScreen =
                            "products"

                    },

                    onProductSaved = {

                        currentScreen =
                            "products"

                    }
                )
            }


            // =================================================
            // REVIEW
            // =================================================

            "review" -> {

                ReviewScreen(

                    businessData =
                        businessData,

                    onBack = {

                        currentScreen =
                            "products"

                    },

                    onSave = {

                        val business =
                            BusinessEntity(

                                businessName =
                                    businessData
                                        .businessName,

                                businessType =
                                    businessData
                                        .businessType,

                                registrationNumber =
                                    businessData
                                        .registrationNumber,

                                businessPhone =
                                    businessData
                                        .businessPhone,

                                businessEmail =
                                    businessData
                                        .businessEmail,

                                ownerName =
                                    businessData
                                        .ownerName,

                                contactPerson =
                                    businessData
                                        .contactPerson,

                                ownerPhone =
                                    businessData
                                        .ownerPhone,

                                ownerEmail =
                                    businessData
                                        .ownerEmail,

                                streetAddress =
                                    businessData
                                        .streetAddress,

                                city =
                                    businessData
                                        .city,

                                province =
                                    businessData
                                        .province,

                                postalCode =
                                    businessData
                                        .postalCode,

                                latitude =
                                    businessData
                                        .latitude,

                                longitude =
                                    businessData
                                        .longitude,

                                storeStatus =
                                    businessData
                                        .storeStatus,

                                productName =
                                    businessData
                                        .productName,

                                productCategory =
                                    businessData
                                        .productCategory,

                                productDescription =
                                    businessData
                                        .productDescription,

                                price =
                                    businessData
                                        .price,

                                quantity =
                                    businessData
                                        .quantity
                            )


                        viewModel.saveBusiness(

                            business =
                                business,

                            products =
                                businessData
                                    .products
                        )


                        currentScreen =
                            "dashboard"

                    }
                )
            }


            // =================================================
            // BUSINESS LIST
            // =================================================

            "businessList" -> {

                BusinessListScreen(

                    viewModel =
                        viewModel,

                    modifier =
                        Modifier.padding(
                            innerPadding
                        ),

                    onBack = {

                        currentScreen =
                            "dashboard"

                    },

                    onBusinessClick = {
                            business ->

                        selectedBusiness =
                            business

                        currentScreen =
                            "businessInfo"

                    }
                )
            }


            // =================================================
            // BUSINESS INFORMATION
            // =================================================

            "businessInfo" -> {

                selectedBusiness?.let {
                        business ->

                    BusinessInfoScreen(

                        business =
                            business,

                        onBack = {

                            selectedBusiness =
                                null

                            currentScreen =
                                "businessList"

                        },

                        onEdit = {

                            currentScreen =
                                "editBusiness"

                        },

                        onDelete = {

                            viewModel.deleteBusiness(
                                business
                            )

                            selectedBusiness =
                                null

                            currentScreen =
                                "businessList"

                        }
                    )
                }
            }


            // =================================================
            // EDIT BUSINESS
            // =================================================

            "editBusiness" -> {

                selectedBusiness?.let {
                        business ->

                    EditBusinessScreen(

                        business =
                            business,

                        productViewModel =
                            productViewModel,

                        onBack = {

                            currentScreen =
                                "businessInfo"

                        },

                        onSave = {
                                updatedBusiness,
                                updatedProducts ->

                            viewModel.updateBusiness(

                                business =
                                    updatedBusiness,

                                products =
                                    updatedProducts
                            )

                            selectedBusiness =
                                updatedBusiness

                            currentScreen =
                                "businessInfo"

                        }
                    )
                }
            }
        }
    }
}


// =============================================================
// DASHBOARD SCREEN
// =============================================================

@Composable
fun DashboardScreen(

    modifier: Modifier =
        Modifier,

    onAddBusiness: () -> Unit,

    onViewBusinesses: () -> Unit

) {

    // ---------------------------------------------------------
    // DARK THEME COLOURS
    // ---------------------------------------------------------

    val background =
        Color(0xFF080A0D)

    val cardBlack =
        Color(0xFF11151A)

    val elevatedBlack =
        Color(0xFF171C22)

    val primaryBlue =
        Color(0xFF0066CC)

    val brightBlue =
        Color(0xFF00AEEF)

    val darkBlue =
        Color(0xFF004B93)

    val white =
        Color(0xFFFFFFFF)

    val secondaryText =
        Color(0xFFB8C0C8)

    val green =
        Color(0xFF35C759)


    Column(

        modifier =
            modifier
                .fillMaxSize()
                .background(
                    background
                )

    ) {

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        Box(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        cardBlack
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 24.dp
                    )

        ) {

            Column {

                Text(

                    text =
                        "BusinessConnect",

                    color =
                        white,

                    style =
                        MaterialTheme.typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold

                )


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                Text(

                    text =
                        "FIELD SALES DASHBOARD",

                    color =
                        brightBlue,

                    style =
                        MaterialTheme.typography
                            .labelMedium,

                    fontWeight =
                        FontWeight.Bold

                )
            }
        }


        // -----------------------------------------------------
        // CONTENT
        // -----------------------------------------------------

        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp
                )

        ) {

            // -------------------------------------------------
            // WELCOME
            // -------------------------------------------------

            item {

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                Text(

                    text =
                        "Welcome back",

                    color =
                        white,

                    style =
                        MaterialTheme.typography
                            .headlineMedium,

                    fontWeight =
                        FontWeight.Bold

                )


                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Text(

                    text =
                        "Manage businesses and capture new sales opportunities.",

                    color =
                        secondaryText,

                    style =
                        MaterialTheme.typography
                            .bodyMedium

                )
            }


            // -------------------------------------------------
            // STATS
            // -------------------------------------------------

            item {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )

                ) {

                    DashboardStatCard(

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        symbol =
                            "B",

                        value =
                            "0",

                        label =
                            "Businesses",

                        iconBackground =
                            darkBlue,

                        iconColor =
                            brightBlue,

                        cardColor =
                            cardBlack,

                        textColor =
                            white,

                        secondaryColor =
                            secondaryText

                    )


                    DashboardStatCard(

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        symbol =
                            "L",

                        value =
                            "0",

                        label =
                            "Locations",

                        iconBackground =
                            Color(0xFF173B28),

                        iconColor =
                            green,

                        cardColor =
                            cardBlack,

                        textColor =
                            white,

                        secondaryColor =
                            secondaryText

                    )
                }
            }


            // -------------------------------------------------
            // PRIMARY ACTION
            // -------------------------------------------------

            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                cardBlack
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                4.dp
                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                22.dp
                            )

                    ) {

                        Text(

                            text =
                                "+",

                            color =
                                brightBlue,

                            style =
                                MaterialTheme.typography
                                    .displaySmall,

                            fontWeight =
                                FontWeight.Bold

                        )


                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )


                        Text(

                            text =
                                "Add a New Business",

                            color =
                                white,

                            style =
                                MaterialTheme.typography
                                    .titleLarge,

                            fontWeight =
                                FontWeight.Bold

                        )


                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )


                        Text(

                            text =
                                "Capture a new business opportunity.",

                            color =
                                secondaryText,

                            style =
                                MaterialTheme.typography
                                    .bodyMedium

                        )


                        Spacer(
                            modifier =
                                Modifier.height(20.dp)
                        )


                        Button(

                            onClick =
                                onAddBusiness,

                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        primaryBlue,

                                    contentColor =
                                        white
                                ),

                            shape =
                                RoundedCornerShape(
                                    12.dp
                                )

                        ) {

                            Text(

                                text =
                                    "START NEW BUSINESS",

                                fontWeight =
                                    FontWeight.Bold

                            )
                        }
                    }
                }
            }


            // -------------------------------------------------
            // DIRECTORY
            // -------------------------------------------------

            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                cardBlack
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                2.dp
                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                20.dp
                            )

                    ) {

                        Text(

                            text =
                                "Business Directory",

                            color =
                                white,

                            style =
                                MaterialTheme.typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.Bold

                        )


                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )


                        Text(

                            text =
                                "View and manage captured businesses.",

                            color =
                                secondaryText,

                            style =
                                MaterialTheme.typography
                                    .bodySmall

                        )


                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )


                        Button(

                            onClick =
                                onViewBusinesses,

                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        elevatedBlack,

                                    contentColor =
                                        brightBlue
                                ),

                            shape =
                                RoundedCornerShape(
                                    10.dp
                                )

                        ) {

                            Text(

                                text =
                                    "VIEW BUSINESSES",

                                fontWeight =
                                    FontWeight.Bold

                            )
                        }
                    }
                }
            }


            // -------------------------------------------------
            // SALES OVERVIEW
            // -------------------------------------------------

            item {

                Text(

                    text =
                        "Sales Overview",

                    color =
                        white,

                    style =
                        MaterialTheme.typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold

                )
            }


            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                cardBlack
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                1.dp
                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                20.dp
                            )

                    ) {

                        Text(

                            text =
                                "Business opportunities",

                            color =
                                white,

                            style =
                                MaterialTheme.typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold

                        )


                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Text(

                            text =
                                "Your captured businesses and product opportunities will appear here.",

                            color =
                                secondaryText,

                            style =
                                MaterialTheme.typography
                                    .bodyMedium

                        )


                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )


                        Surface(

                            modifier =
                                Modifier.fillMaxWidth(),

                            shape =
                                RoundedCornerShape(
                                    12.dp
                                ),

                            color =
                                elevatedBlack

                        ) {

                            Text(

                                text =
                                    "NO BUSINESS DATA YET",

                                modifier =
                                    Modifier.padding(
                                        14.dp
                                    ),

                                color =
                                    brightBlue,

                                style =
                                    MaterialTheme.typography
                                        .bodySmall,

                                fontWeight =
                                    FontWeight.Bold

                            )
                        }
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}


// =============================================================
// DASHBOARD STAT CARD
// =============================================================

@Composable
private fun DashboardStatCard(

    modifier: Modifier,

    symbol: String,

    value: String,

    label: String,

    iconBackground: Color,

    iconColor: Color,

    cardColor: Color,

    textColor: Color,

    secondaryColor: Color

) {

    Card(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                18.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    cardColor
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    2.dp
            )

    ) {

        Column(

            modifier =
                Modifier.padding(
                    18.dp
                )

        ) {

            Box(

                modifier =
                    Modifier
                        .size(42.dp)
                        .clip(
                            RoundedCornerShape(
                                12.dp
                            )
                        )
                        .background(
                            iconBackground
                        ),

                contentAlignment =
                    Alignment.Center

            ) {

                Text(

                    text =
                        symbol,

                    color =
                        iconColor,

                    style =
                        MaterialTheme.typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold

                )
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )


            Text(

                text =
                    value,

                color =
                    textColor,

                style =
                    MaterialTheme.typography
                        .headlineMedium,

                fontWeight =
                    FontWeight.Bold

            )


            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )


            Text(

                text =
                    label,

                color =
                    secondaryColor,

                style =
                    MaterialTheme.typography
                        .bodySmall

            )
        }
    }
}
