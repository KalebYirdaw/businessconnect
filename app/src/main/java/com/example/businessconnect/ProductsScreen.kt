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
fun ProductsScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var productName by remember {
        mutableStateOf(businessData.productName)
    }

    var productCategory by remember {
        mutableStateOf(businessData.productCategory)
    }

    var productDescription by remember {
        mutableStateOf(businessData.productDescription)
    }

    var price by remember {
        mutableStateOf(businessData.price)
    }

    var quantity by remember {
        mutableStateOf(businessData.quantity)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        TextButton(
            onClick = {
                onBack()
            }
        ) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Products",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 4 of 4 • Product Details",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = productName,
            onValueChange = {
                productName = it
            },
            label = {
                Text("Product Name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = productCategory,
            onValueChange = {
                productCategory = it
            },
            label = {
                Text("Product Category")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = productDescription,
            onValueChange = {
                productDescription = it
            },
            label = {
                Text("Product Description")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = price,
            onValueChange = {
                price = it
            },
            label = {
                Text("Price")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it
            },
            label = {
                Text("Quantity")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                businessData.productName = productName
                businessData.productCategory = productCategory
                businessData.productDescription = productDescription
                businessData.price = price
                businessData.quantity = quantity

                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Review & Save")
        }
    }
}