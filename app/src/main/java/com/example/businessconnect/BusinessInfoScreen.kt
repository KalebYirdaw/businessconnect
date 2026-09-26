package com.example.businessconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
fun BusinessInfoScreen(
    business: BusinessEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = business.businessName.ifBlank {
                "Business Information"
            },
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // EDIT BUTTON
        // ==================================================

        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Business")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // ==================================================
        // DELETE BUTTON
        // ==================================================

        TextButton(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Delete Business",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ==================================================
        // BUSINESS DETAILS
        // ==================================================

        InfoSection(
            title = "Business Details"
        ) {

            InfoRow(
                label = "Business Name",
                value = business.businessName
            )

            InfoRow(
                label = "Business Type",
                value = business.businessType
            )

            InfoRow(
                label = "Registration Number",
                value = business.registrationNumber
            )

            InfoRow(
                label = "Business Phone",
                value = business.businessPhone
            )

            InfoRow(
                label = "Business Email",
                value = business.businessEmail
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // OWNER DETAILS
        // ==================================================

        InfoSection(
            title = "Owner Details"
        ) {

            InfoRow(
                label = "Owner Name",
                value = business.ownerName
            )

            InfoRow(
                label = "Contact Person",
                value = business.contactPerson
            )

            InfoRow(
                label = "Owner Phone",
                value = business.ownerPhone
            )

            InfoRow(
                label = "Owner Email",
                value = business.ownerEmail
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // LOCATION
        // ==================================================

        InfoSection(
            title = "Location"
        ) {

            InfoRow(
                label = "Street Address",
                value = business.streetAddress
            )

            InfoRow(
                label = "City",
                value = business.city
            )

            InfoRow(
                label = "Province",
                value = business.province
            )

            InfoRow(
                label = "Postal Code",
                value = business.postalCode
            )

            InfoRow(
                label = "Latitude",
                value = business.latitude
            )

            InfoRow(
                label = "Longitude",
                value = business.longitude
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // PRODUCT
        // ==================================================

        InfoSection(
            title = "Product"
        ) {

            InfoRow(
                label = "Product Name",
                value = business.productName
            )

            InfoRow(
                label = "Category",
                value = business.productCategory
            )

            InfoRow(
                label = "Description",
                value = business.productDescription
            )

            InfoRow(
                label = "Price",
                value = business.price
            )

            InfoRow(
                label = "Quantity",
                value = business.quantity
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }

    // ======================================================
    // DELETE CONFIRMATION
    // ======================================================

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Business?")
            },

            text = {
                Text(
                    "Are you sure you want to delete " +
                            "\"${business.businessName}\"? " +
                            "This action cannot be undone."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showDeleteDialog = false

                        onDelete()
                    }
                ) {

                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )

        Text(
            text = value.ifBlank {
                "Not provided"
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}