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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProductsScreen(

    businessData: BusinessData,

    productCatalogueViewModel:
    ProductCatalogueViewModel,

    onBack: () -> Unit,

    onNext: () -> Unit,

    onAddProduct: () -> Unit

) {

    // ---------------------------------------------------------
    // SELECTED PRODUCTS
    // ---------------------------------------------------------

    val selectedProducts =
        remember {
            mutableStateListOf<ProductAssessment>()
        }


    // ---------------------------------------------------------
    // PRODUCTS FROM ROOM DATABASE
    // ---------------------------------------------------------

    val catalogueProducts by
    productCatalogueViewModel
        .products
        .collectAsState(
            initial = emptyList()
        )


    // ---------------------------------------------------------
    // SCREEN
    // ---------------------------------------------------------

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp)

    ) {


        // -----------------------------------------------------
        // BACK
        // -----------------------------------------------------

        TextButton(

            onClick = onBack

        ) {

            Text(
                "← Back"
            )

        }


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        Text(

            text =
                "Product Assessment",

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
                "Select the products relevant to this store and record their availability.",

            style =
                MaterialTheme.typography
                    .bodyLarge

        )


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )


        // -----------------------------------------------------
        // CATALOGUE HEADER
        // -----------------------------------------------------

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Text(

                text =
                    "Product Catalogue",

                style =
                    MaterialTheme.typography
                        .titleLarge,

                modifier =
                    Modifier.weight(1f)

            )


            TextButton(

                onClick =
                    onAddProduct

            ) {

                Text(
                    "+ Add Product"
                )

            }
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // -----------------------------------------------------
        // PRODUCT CATALOGUE
        // -----------------------------------------------------

        if (catalogueProducts.isEmpty()) {

            Text(

                text =
                    "No products available.",

                style =
                    MaterialTheme.typography
                        .bodyMedium

            )
        }


        catalogueProducts.forEach { catalogueProduct ->

            val isSelected =
                selectedProducts.any {

                    it.productName ==
                            catalogueProduct.name

                }


            Card(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 8.dp
                        )

            ) {

                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    // -------------------------------------------------
                    // CHECKBOX
                    // -------------------------------------------------

                    Checkbox(

                        checked =
                            isSelected,

                        onCheckedChange = {
                                checked ->

                            if (checked) {

                                selectedProducts.add(

                                    ProductAssessment(

                                        productName =
                                            catalogueProduct
                                                .name,

                                        productCategory =
                                            catalogueProduct
                                                .category

                                    )

                                )

                            } else {

                                selectedProducts
                                    .removeAll {

                                        it.productName ==
                                                catalogueProduct
                                                    .name

                                    }
                            }
                        }
                    )


                    // -------------------------------------------------
                    // PRODUCT INFORMATION
                    // -------------------------------------------------

                    Column {

                        Text(

                            text =
                                catalogueProduct.name,

                            style =
                                MaterialTheme.typography
                                    .titleMedium

                        )


                        Text(

                            text =
                                catalogueProduct.category,

                            style =
                                MaterialTheme.typography
                                    .bodySmall

                        )
                    }
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )


        // -----------------------------------------------------
        // SELECTED PRODUCTS
        // -----------------------------------------------------

        if (selectedProducts.isNotEmpty()) {

            Text(

                text =
                    "Selected Products",

                style =
                    MaterialTheme.typography
                        .titleLarge

            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            selectedProducts.forEachIndexed {
                    index,
                    product ->

                ProductAssessmentCard(

                    product =
                        product,

                    onProductChanged = {
                            updatedProduct ->

                        selectedProducts[index] =
                            updatedProduct

                    }
                )


                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(16.dp)
        )


        // -----------------------------------------------------
        // NEXT
        // -----------------------------------------------------

        Button(

            onClick = {

                businessData.products =
                    selectedProducts.toList()


                // Keep the older single-product
                // fields updated for compatibility.

                if (
                    selectedProducts.isNotEmpty()
                ) {

                    val firstProduct =
                        selectedProducts.first()


                    businessData.productName =
                        firstProduct.productName


                    businessData.productCategory =
                        firstProduct.productCategory


                    businessData.productDescription =
                        firstProduct.notes


                    businessData.price =
                        firstProduct.price


                    businessData.quantity =
                        firstProduct.quantity

                }


                onNext()
            },

            modifier =
                Modifier.fillMaxWidth()

        ) {

            Text(
                "Next"
            )
        }


        Spacer(
            modifier =
                Modifier.height(16.dp)
        )
    }
}


// =============================================================
// PRODUCT ASSESSMENT CARD
// =============================================================

@Composable
private fun ProductAssessmentCard(

    product:
    ProductAssessment,

    onProductChanged:
        (ProductAssessment) -> Unit

) {

    var quantity by remember(
        product.productName
    ) {

        mutableStateOf(
            product.quantity
        )
    }


    var price by remember(
        product.productName
    ) {

        mutableStateOf(
            product.price
        )
    }


    var notes by remember(
        product.productName
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


            // -------------------------------------------------
            // PRODUCT NAME
            // -------------------------------------------------

            Text(

                text =
                    product.productName,

                style =
                    MaterialTheme.typography
                        .titleLarge

            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            // -------------------------------------------------
            // CATEGORY
            // -------------------------------------------------

            Text(

                text =
                    product.productCategory,

                style =
                    MaterialTheme.typography
                        .bodyMedium

            )


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // -------------------------------------------------
            // AVAILABILITY
            // -------------------------------------------------

            Text(

                text =
                    "Availability",

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


                Text(
                    "Present"
                )


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


                Text(
                    "Missing"
                )
            }


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            // -------------------------------------------------
            // QUANTITY
            // -------------------------------------------------

            OutlinedTextField(

                value =
                    quantity,

                onValueChange = {
                        value ->

                    quantity =
                        value

                    onProductChanged(

                        product.copy(
                            quantity =
                                value
                        )

                    )
                },

                label = {
                    Text(
                        "Quantity"
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            // -------------------------------------------------
            // PRICE
            // -------------------------------------------------

            OutlinedTextField(

                value =
                    price,

                onValueChange = {
                        value ->

                    price =
                        value

                    onProductChanged(

                        product.copy(
                            price =
                                value
                        )

                    )
                },

                label = {
                    Text(
                        "Price"
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            // -------------------------------------------------
            // NOTES
            // -------------------------------------------------

            OutlinedTextField(

                value =
                    notes,

                onValueChange = {
                        value ->

                    notes =
                        value

                    onProductChanged(

                        product.copy(
                            notes =
                                value
                        )

                    )
                },

                label = {
                    Text(
                        "Notes"
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 3
            )
        }
    }
}