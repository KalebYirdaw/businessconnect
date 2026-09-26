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
fun AddProductScreen(
    productCatalogueViewModel:
    ProductCatalogueViewModel,

    onBack: () -> Unit,

    onProductSaved: () -> Unit
) {

    var productName by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
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
            text = "Add New Product",

            style =
                MaterialTheme.typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text =
                "Add a product to the catalogue. It will be available when assessing future stores.",

            style =
                MaterialTheme.typography
                    .bodyLarge
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = productName,

            onValueChange = {
                productName = it
            },

            label = {
                Text("Product Name")
            },

            placeholder = {
                Text("Enter product name")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = category,

            onValueChange = {
                category = it
            },

            label = {
                Text("Category")
            },

            placeholder = {
                Text("e.g. Beverages")
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier =
                Modifier.height(32.dp)
        )

        Button(
            onClick = {

                if (
                    productName.isNotBlank() &&
                    category.isNotBlank()
                ) {

                    productCatalogueViewModel
                        .addProduct(
                            name =
                                productName,

                            category =
                                category
                        )

                    onProductSaved()
                }
            },

            modifier =
                Modifier.fillMaxWidth(),

            enabled =
                productName.isNotBlank() &&
                        category.isNotBlank()
        ) {

            Text("Save Product")
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Text(
            text =
                "Both fields are required.",

            style =
                MaterialTheme.typography
                    .bodySmall
        )
    }
}