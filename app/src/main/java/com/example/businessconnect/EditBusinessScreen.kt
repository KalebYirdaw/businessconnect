package com.example.businessconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditBusinessScreen(
    business: BusinessEntity,
    onBack: () -> Unit,
    onSave: (BusinessEntity) -> Unit
) {

    /*
     * Create a copy of the existing business information.
     *
     * IMPORTANT:
     * The entire BusinessData object is Compose state.
     * When we use data = data.copy(...),
     * Compose knows the screen needs to update.
     */
    var data by remember {

        mutableStateOf(
            BusinessData(

                businessName = business.businessName,
                businessType = business.businessType,
                registrationNumber = business.registrationNumber,

                businessPhone = business.businessPhone,
                businessEmail = business.businessEmail,

                ownerName = business.ownerName,
                contactPerson = business.contactPerson,
                ownerPhone = business.ownerPhone,
                ownerEmail = business.ownerEmail,

                streetAddress = business.streetAddress,
                city = business.city,
                province = business.province,
                postalCode = business.postalCode,

                latitude = business.latitude,
                longitude = business.longitude,

                productName = business.productName,
                productCategory = business.productCategory,
                productDescription = business.productDescription,

                price = business.price,
                quantity = business.quantity
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // ==================================================
        // BACK
        // ==================================================

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // ==================================================
        // TITLE
        // ==================================================

        Text(
            text = "Edit Business",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==================================================
        // BUSINESS DETAILS
        // ==================================================

        Text(
            text = "Business Details",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        EditField(
            label = "Business Name",
            value = data.businessName,
            onValueChange = {
                data = data.copy(
                    businessName = it
                )
            }
        )

        EditField(
            label = "Business Type",
            value = data.businessType,
            onValueChange = {
                data = data.copy(
                    businessType = it
                )
            }
        )

        EditField(
            label = "Registration Number",
            value = data.registrationNumber,
            onValueChange = {
                data = data.copy(
                    registrationNumber = it
                )
            }
        )

        EditField(
            label = "Business Phone",
            value = data.businessPhone,
            onValueChange = {
                data = data.copy(
                    businessPhone = it
                )
            }
        )

        EditField(
            label = "Business Email",
            value = data.businessEmail,
            onValueChange = {
                data = data.copy(
                    businessEmail = it
                )
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==================================================
        // OWNER DETAILS
        // ==================================================

        Text(
            text = "Owner Details",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        EditField(
            label = "Owner Name",
            value = data.ownerName,
            onValueChange = {
                data = data.copy(
                    ownerName = it
                )
            }
        )

        EditField(
            label = "Contact Person",
            value = data.contactPerson,
            onValueChange = {
                data = data.copy(
                    contactPerson = it
                )
            }
        )

        EditField(
            label = "Owner Phone",
            value = data.ownerPhone,
            onValueChange = {
                data = data.copy(
                    ownerPhone = it
                )
            }
        )

        EditField(
            label = "Owner Email",
            value = data.ownerEmail,
            onValueChange = {
                data = data.copy(
                    ownerEmail = it
                )
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==================================================
        // LOCATION
        // ==================================================

        Text(
            text = "Location",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        EditField(
            label = "Street Address",
            value = data.streetAddress,
            onValueChange = {
                data = data.copy(
                    streetAddress = it
                )
            }
        )

        EditField(
            label = "City",
            value = data.city,
            onValueChange = {
                data = data.copy(
                    city = it
                )
            }
        )

        EditField(
            label = "Province",
            value = data.province,
            onValueChange = {
                data = data.copy(
                    province = it
                )
            }
        )

        EditField(
            label = "Postal Code",
            value = data.postalCode,
            onValueChange = {
                data = data.copy(
                    postalCode = it
                )
            }
        )

        EditField(
            label = "Latitude",
            value = data.latitude,
            onValueChange = {
                data = data.copy(
                    latitude = it
                )
            }
        )

        EditField(
            label = "Longitude",
            value = data.longitude,
            onValueChange = {
                data = data.copy(
                    longitude = it
                )
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==================================================
        // PRODUCT
        // ==================================================

        Text(
            text = "Product",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        EditField(
            label = "Product Name",
            value = data.productName,
            onValueChange = {
                data = data.copy(
                    productName = it
                )
            }
        )

        EditField(
            label = "Product Category",
            value = data.productCategory,
            onValueChange = {
                data = data.copy(
                    productCategory = it
                )
            }
        )

        EditField(
            label = "Product Description",
            value = data.productDescription,
            onValueChange = {
                data = data.copy(
                    productDescription = it
                )
            },
            singleLine = false
        )

        EditField(
            label = "Price",
            value = data.price,
            onValueChange = {
                data = data.copy(
                    price = it
                )
            }
        )

        EditField(
            label = "Quantity",
            value = data.quantity,
            onValueChange = {
                data = data.copy(
                    quantity = it
                )
            }
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // ==================================================
        // SAVE CHANGES
        // ==================================================

        Button(
            onClick = {

                /*
                 * Keep the original Room ID.
                 *
                 * This makes Room UPDATE the existing
                 * business instead of creating a new one.
                 */
                val updatedBusiness = BusinessEntity(

                    id = business.id,

                    businessName = data.businessName,
                    businessType = data.businessType,
                    registrationNumber = data.registrationNumber,

                    businessPhone = data.businessPhone,
                    businessEmail = data.businessEmail,

                    ownerName = data.ownerName,
                    contactPerson = data.contactPerson,
                    ownerPhone = data.ownerPhone,
                    ownerEmail = data.ownerEmail,

                    streetAddress = data.streetAddress,
                    city = data.city,
                    province = data.province,
                    postalCode = data.postalCode,

                    latitude = data.latitude,
                    longitude = data.longitude,

                    productName = data.productName,
                    productCategory = data.productCategory,
                    productDescription = data.productDescription,

                    price = data.price,
                    quantity = data.quantity
                )

                onSave(updatedBusiness)
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Save Changes"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}


// ==========================================================
// EDIT FIELD
// ==========================================================

@Composable
private fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true
) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),

        singleLine = singleLine
    )
}