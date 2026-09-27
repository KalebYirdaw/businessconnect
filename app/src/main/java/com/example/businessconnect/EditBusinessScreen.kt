package com.example.businessconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val EditBackground = Color(0xFF080A0D)
private val EditCard = Color(0xFF11151A)
private val EditElevated = Color(0xFF171C22)
private val EditBlue = Color(0xFF0066CC)
private val EditBrightBlue = Color(0xFF00AEEF)
private val EditWhite = Color(0xFFFFFFFF)
private val EditSecondary = Color(0xFFB8C0C8)
private val EditBorder = Color(0xFF28313A)
private val EditGreen = Color(0xFF35C759)
private val EditRed = Color(0xFFE55353)

@Composable
fun EditBusinessScreen(
    business: BusinessEntity,
    productViewModel: ProductViewModel,
    onBack: () -> Unit,
    onSave: (
        BusinessEntity,
        List<ProductEntity>
    ) -> Unit
) {

    var businessName by remember {
        mutableStateOf(business.businessName)
    }

    var businessType by remember {
        mutableStateOf(business.businessType)
    }

    var registrationNumber by remember {
        mutableStateOf(business.registrationNumber)
    }

    var businessPhone by remember {
        mutableStateOf(business.businessPhone)
    }

    var businessEmail by remember {
        mutableStateOf(business.businessEmail)
    }

    var ownerName by remember {
        mutableStateOf(business.ownerName)
    }

    var contactPerson by remember {
        mutableStateOf(business.contactPerson)
    }

    var ownerPhone by remember {
        mutableStateOf(business.ownerPhone)
    }

    var ownerEmail by remember {
        mutableStateOf(business.ownerEmail)
    }

    var streetAddress by remember {
        mutableStateOf(business.streetAddress)
    }

    var city by remember {
        mutableStateOf(business.city)
    }

    var province by remember {
        mutableStateOf(business.province)
    }

    var postalCode by remember {
        mutableStateOf(business.postalCode)
    }

    var latitude by remember {
        mutableStateOf(business.latitude)
    }

    var longitude by remember {
        mutableStateOf(business.longitude)
    }

    var storeStatus by remember {
        mutableStateOf(business.storeStatus)
    }

    val products = remember {
        mutableStateListOf<ProductEntity>()
    }

    var showSaveDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(business.id) {

        val savedProducts =
            productViewModel.getProductsForBusinessOnce(
                business.id
            )

        products.clear()
        products.addAll(savedProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EditBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 0.dp)
        ) {
            Text(
                text = "←  BACK",
                color = EditBrightBlue,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "EDIT BUSINESS",
            color = EditWhite,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Update the captured business information.",
            color = EditSecondary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        // ---------------------------------------------------------
        // BUSINESS PROFILE
        // ---------------------------------------------------------

        EditSectionHeader(
            number = "01",
            title = "BUSINESS DETAILS",
            subtitle = "Company and store information"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        EditCard {

            EditField(
                value = businessName,
                onValueChange = {
                    businessName = it
                },
                label = "Business / Store Name"
            )

            EditField(
                value = businessType,
                onValueChange = {
                    businessType = it
                },
                label = "Business Type"
            )

            EditField(
                value = registrationNumber,
                onValueChange = {
                    registrationNumber = it
                },
                label = "Registration Number"
            )

            EditField(
                value = businessPhone,
                onValueChange = {
                    businessPhone = it
                },
                label = "Phone Number"
            )

            EditField(
                value = businessEmail,
                onValueChange = {
                    businessEmail = it
                },
                label = "Email Address"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "STORE STATUS",
                color = EditSecondary,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            StatusSelector(
                selected = storeStatus,
                onSelected = {
                    storeStatus = it
                }
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ---------------------------------------------------------
        // OWNER / CONTACT
        // ---------------------------------------------------------

        EditSectionHeader(
            number = "02",
            title = "OWNER / CONTACT",
            subtitle = "Primary business contact"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        EditCard {

            EditField(
                value = ownerName,
                onValueChange = {
                    ownerName = it
                },
                label = "Owner Full Name"
            )

            EditField(
                value = contactPerson,
                onValueChange = {
                    contactPerson = it
                },
                label = "Contact Person"
            )

            EditField(
                value = ownerPhone,
                onValueChange = {
                    ownerPhone = it
                },
                label = "Contact Phone"
            )

            EditField(
                value = ownerEmail,
                onValueChange = {
                    ownerEmail = it
                },
                label = "Contact Email"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ---------------------------------------------------------
        // LOCATION
        // ---------------------------------------------------------

        EditSectionHeader(
            number = "03",
            title = "LOCATION",
            subtitle = "Business address and coordinates"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        EditCard {

            EditField(
                value = streetAddress,
                onValueChange = {
                    streetAddress = it
                },
                label = "Street Address"
            )

            EditField(
                value = city,
                onValueChange = {
                    city = it
                },
                label = "City"
            )

            EditField(
                value = province,
                onValueChange = {
                    province = it
                },
                label = "Province"
            )

            EditField(
                value = postalCode,
                onValueChange = {
                    postalCode = it
                },
                label = "Postal Code"
            )

            EditField(
                value = latitude,
                onValueChange = {
                    latitude = it
                },
                label = "Latitude"
            )

            EditField(
                value = longitude,
                onValueChange = {
                    longitude = it
                },
                label = "Longitude"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ---------------------------------------------------------
        // PRODUCT ASSESSMENT
        // ---------------------------------------------------------

        EditSectionHeader(
            number = "04",
            title = "PRODUCT ASSESSMENT",
            subtitle = "Update products and availability"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        if (products.isEmpty()) {

            EditCard {

                Text(
                    text = "NO PRODUCT ASSESSMENTS",
                    color = EditWhite,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "No product assessments are currently linked to this business.",
                    color = EditSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        } else {

            products.forEachIndexed { index, product ->

                EditableProductCard(
                    product = product,
                    onProductChanged = { updatedProduct ->

                        products[index] = updatedProduct
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // ---------------------------------------------------------
        // SAVE
        // ---------------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = EditCard,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 1.dp,
                    color = EditBorder,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(18.dp)
        ) {

            Text(
                text = "SAVE CHANGES",
                color = EditWhite,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Review your changes before updating this business record.",
                color = EditSecondary,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {
                    showSaveDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EditBlue,
                    contentColor = EditWhite
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "SAVE CHANGES  ✓"
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "CANCEL",
                    color = EditSecondary
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }

    // -------------------------------------------------------------
    // SAVE CONFIRMATION
    // -------------------------------------------------------------

    if (showSaveDialog) {

        AlertDialog(
            onDismissRequest = {
                showSaveDialog = false
            },
            containerColor = EditCard,
            titleContentColor = EditWhite,
            textContentColor = EditSecondary,
            title = {
                Text(
                    text = "Save Changes?"
                )
            },
            text = {
                Text(
                    text = "This will update the business record with the information currently entered."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        val updatedBusiness =
                            business.copy(

                                businessName =
                                    businessName,

                                businessType =
                                    businessType,

                                registrationNumber =
                                    registrationNumber,

                                businessPhone =
                                    businessPhone,

                                businessEmail =
                                    businessEmail,

                                ownerName =
                                    ownerName,

                                contactPerson =
                                    contactPerson,

                                ownerPhone =
                                    ownerPhone,

                                ownerEmail =
                                    ownerEmail,

                                streetAddress =
                                    streetAddress,

                                city =
                                    city,

                                province =
                                    province,

                                postalCode =
                                    postalCode,

                                latitude =
                                    latitude,

                                longitude =
                                    longitude,

                                storeStatus =
                                    storeStatus
                            )

                        showSaveDialog = false

                        onSave(
                            updatedBusiness,
                            products.toList()
                        )
                    }
                ) {
                    Text(
                        text = "SAVE",
                        color = EditBrightBlue
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showSaveDialog = false
                    }
                ) {
                    Text(
                        text = "CANCEL",
                        color = EditSecondary
                    )
                }
            }
        )
    }
}

@Composable
private fun EditSectionHeader(
    number: String,
    title: String,
    subtitle: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.width(42.dp)
        ) {

            Text(
                text = number,
                color = EditBrightBlue,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = EditWhite,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = subtitle,
                color = EditSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun EditCard(
    content: @Composable () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = EditCard,
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = EditBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
private fun EditField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
private fun StatusSelector(
    selected: String,
    onSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        StatusOption(
            label = "New Prospect",
            selected = selected == "New Prospect",
            onClick = {
                onSelected("New Prospect")
            },
            modifier = Modifier.weight(1f)
        )

        StatusOption(
            label = "Existing Store",
            selected = selected == "Existing Store",
            onClick = {
                onSelected("Existing Store")
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatusOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .background(
                color = if (selected) {
                    EditElevated
                } else {
                    EditCard
                },
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) {
                    EditBlue
                } else {
                    EditBorder
                },
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = label,
            color = if (selected) {
                EditWhite
            } else {
                EditSecondary
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun EditableProductCard(
    product: ProductEntity,
    onProductChanged: (ProductEntity) -> Unit
) {

    var quantity by remember(product.id) {
        mutableStateOf(product.quantity)
    }

    var price by remember(product.id) {
        mutableStateOf(product.price)
    }

    var notes by remember(product.id) {
        mutableStateOf(product.notes)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = EditCard,
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = EditBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.productName,
                    color = EditWhite,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = product.productCategory,
                    color = EditSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = if (product.availability == "Present") {
                    "PRESENT"
                } else {
                    "MISSING"
                },
                color = if (product.availability == "Present") {
                    EditGreen
                } else {
                    EditRed
                },
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = "AVAILABILITY",
            color = EditSecondary,
            style = MaterialTheme.typography.labelMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = product.availability == "Present",
                onClick = {
                    onProductChanged(
                        product.copy(
                            availability = "Present"
                        )
                    )
                }
            )

            Text(
                text = "Present",
                color = EditWhite
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            RadioButton(
                selected = product.availability == "Missing",
                onClick = {
                    onProductChanged(
                        product.copy(
                            availability = "Missing"
                        )
                    )
                }
            )

            Text(
                text = "Missing",
                color = EditWhite
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it

                onProductChanged(
                    product.copy(
                        quantity = it
                    )
                )
            },
            label = {
                Text("Quantity")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = price,
            onValueChange = {
                price = it

                onProductChanged(
                    product.copy(
                        price = it
                    )
                )
            },
            label = {
                Text("Price")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = notes,
            onValueChange = {
                notes = it

                onProductChanged(
                    product.copy(
                        notes = it
                    )
                )
            },
            label = {
                Text("Notes")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            shape = RoundedCornerShape(10.dp)
        )
    }
}