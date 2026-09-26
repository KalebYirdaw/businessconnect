package com.example.businessconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

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
        mutableStateOf(
            business.businessName
        )
    }

    var businessType by remember {
        mutableStateOf(
            business.businessType
        )
    }

    var registrationNumber by remember {
        mutableStateOf(
            business.registrationNumber
        )
    }

    var businessPhone by remember {
        mutableStateOf(
            business.businessPhone
        )
    }

    var businessEmail by remember {
        mutableStateOf(
            business.businessEmail
        )
    }

    var ownerName by remember {
        mutableStateOf(
            business.ownerName
        )
    }

    var contactPerson by remember {
        mutableStateOf(
            business.contactPerson
        )
    }

    var ownerPhone by remember {
        mutableStateOf(
            business.ownerPhone
        )
    }

    var ownerEmail by remember {
        mutableStateOf(
            business.ownerEmail
        )
    }

    var streetAddress by remember {
        mutableStateOf(
            business.streetAddress
        )
    }

    var city by remember {
        mutableStateOf(
            business.city
        )
    }

    var province by remember {
        mutableStateOf(
            business.province
        )
    }

    var postalCode by remember {
        mutableStateOf(
            business.postalCode
        )
    }

    var latitude by remember {
        mutableStateOf(
            business.latitude
        )
    }

    var longitude by remember {
        mutableStateOf(
            business.longitude
        )
    }

    var storeStatus by remember {
        mutableStateOf(
            business.storeStatus
        )
    }

    val products =
        remember {
            mutableStateListOf<ProductEntity>()
        }

    LaunchedEffect(
        business.id
    ) {

        val savedProducts =
            productViewModel
                .getProductsForBusinessOnce(
                    business.id
                )

        products.clear()

        products.addAll(
            savedProducts
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {

            Text("← Back")
        }

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text = "Edit Business",

            style =
                MaterialTheme.typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * BUSINESS DETAILS
         */

        Text(
            text = "Business Details",

            style =
                MaterialTheme.typography
                    .titleLarge
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = businessName,

            onValueChange = {
                businessName = it
            },

            label = {
                Text("Business / Store Name")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = businessType,

            onValueChange = {
                businessType = it
            },

            label = {
                Text("Business Type")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = registrationNumber,

            onValueChange = {
                registrationNumber = it
            },

            label = {
                Text("Registration Number")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = businessPhone,

            onValueChange = {
                businessPhone = it
            },

            label = {
                Text("Phone Number")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = businessEmail,

            onValueChange = {
                businessEmail = it
            },

            label = {
                Text("Email Address")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Text(
            text = "Store Status",

            style =
                MaterialTheme.typography
                    .titleMedium
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            RadioButton(
                selected =
                    storeStatus ==
                            "New Prospect",

                onClick = {
                    storeStatus =
                        "New Prospect"
                }
            )

            Text("New Prospect")
        }

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            RadioButton(
                selected =
                    storeStatus ==
                            "Existing Store",

                onClick = {
                    storeStatus =
                        "Existing Store"
                }
            )

            Text("Existing Store")
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * OWNER DETAILS
         */

        Text(
            text = "Owner / Contact",

            style =
                MaterialTheme.typography
                    .titleLarge
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = ownerName,

            onValueChange = {
                ownerName = it
            },

            label = {
                Text("Owner Name")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = contactPerson,

            onValueChange = {
                contactPerson = it
            },

            label = {
                Text("Contact Person")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = ownerPhone,

            onValueChange = {
                ownerPhone = it
            },

            label = {
                Text("Contact Phone")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = ownerEmail,

            onValueChange = {
                ownerEmail = it
            },

            label = {
                Text("Contact Email")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * LOCATION
         */

        Text(
            text = "Location",

            style =
                MaterialTheme.typography
                    .titleLarge
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = streetAddress,

            onValueChange = {
                streetAddress = it
            },

            label = {
                Text("Street Address")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = city,

            onValueChange = {
                city = it
            },

            label = {
                Text("City")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = province,

            onValueChange = {
                province = it
            },

            label = {
                Text("Province")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = postalCode,

            onValueChange = {
                postalCode = it
            },

            label = {
                Text("Postal Code")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = latitude,

            onValueChange = {
                latitude = it
            },

            label = {
                Text("Latitude")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = longitude,

            onValueChange = {
                longitude = it
            },

            label = {
                Text("Longitude")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * PRODUCTS
         */

        Text(
            text = "Product Assessment",

            style =
                MaterialTheme.typography
                    .titleLarge
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        if (products.isEmpty()) {

            Text(
                text =
                    "No product assessments recorded.",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

        } else {

            products.forEachIndexed {
                    index,
                    product ->

                EditableProductCard(
                    product =
                        product,

                    onProductChanged = {
                            updatedProduct ->

                        products[index] =
                            updatedProduct
                    }
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Button(
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

                onSave(
                    updatedBusiness,
                    products.toList()
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Save Changes")
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )
    }
}

@Composable
private fun EditableProductCard(
    product: ProductEntity,
    onProductChanged:
        (ProductEntity) -> Unit
) {

    var quantity by remember(
        product.id
    ) {
        mutableStateOf(
            product.quantity
        )
    }

    var price by remember(
        product.id
    ) {
        mutableStateOf(
            product.price
        )
    }

    var notes by remember(
        product.id
    ) {
        mutableStateOf(
            product.notes
        )
    }

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    product.productName,

                style =
                    MaterialTheme.typography
                        .titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(
                text =
                    product.productCategory,

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = "Availability",

                style =
                    MaterialTheme.typography
                        .titleSmall
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                RadioButton(
                    selected =
                        product.availability ==
                                "Present",

                    onClick = {

                        onProductChanged(
                            product.copy(
                                availability =
                                    "Present"
                            )
                        )
                    }
                )

                Text("Present")

                RadioButton(
                    selected =
                        product.availability ==
                                "Missing",

                    onClick = {

                        onProductChanged(
                            product.copy(
                                availability =
                                    "Missing"
                            )
                        )
                    }
                )

                Text("Missing")
            }

            Spacer(
                modifier =
                    Modifier.height(8.dp)
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

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
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

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
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

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 2
            )
        }
    }
}