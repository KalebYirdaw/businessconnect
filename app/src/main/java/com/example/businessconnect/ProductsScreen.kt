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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ProductsScreen(
    businessData: BusinessData,
    productCatalogueViewModel: ProductCatalogueViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onAddProduct: () -> Unit
) {

    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val elevatedBlack = Color(0xFF171C22)
    val primaryBlue = Color(0xFF0066CC)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)
    val green = Color(0xFF35C759)
    val red = Color(0xFFFF5A67)

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
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        // -----------------------------------------------------
        // BACK
        // -----------------------------------------------------

        TextButton(
            onClick = onBack
        ) {

            Text(
                text = "←  BACK",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        Text(
            text = "PRODUCT ASSESSMENT",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Identify products relevant to this store and assess availability.",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // -----------------------------------------------------
        // PROGRESS
        // -----------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "PRODUCTS & ASSESSMENT",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "04 / 04",
                    color = secondaryText,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                ProductProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProductProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProductProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProductProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // -----------------------------------------------------
        // CATALOGUE CARD
        // -----------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "PRODUCT CATALOGUE",
                        color = white,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Select products relevant to this store.",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                TextButton(
                    onClick = onAddProduct
                ) {

                    Text(
                        text = "+ ADD",
                        color = brightBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            if (catalogueProducts.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(elevatedBlack)
                        .padding(18.dp)
                ) {

                    Text(
                        text = "NO PRODUCTS AVAILABLE",
                        color = white,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Add a product to the catalogue to begin.",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            catalogueProducts.forEach { catalogueProduct ->

                val isSelected =
                    selectedProducts.any {
                        it.productName ==
                                catalogueProduct.name
                    }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(elevatedBlack)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) {
                                brightBlue
                            } else {
                                borderColor
                            },
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(12.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->

                                if (checked) {

                                    selectedProducts.add(
                                        ProductAssessment(
                                            productName =
                                                catalogueProduct.name,

                                            productCategory =
                                                catalogueProduct.category
                                        )
                                    )

                                } else {

                                    selectedProducts.removeAll {

                                        it.productName ==
                                                catalogueProduct.name
                                    }
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = brightBlue,
                                uncheckedColor = secondaryText,
                                checkmarkColor = white
                            )
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = catalogueProduct.name,
                                color = white,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = catalogueProduct.category,
                                color = secondaryText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        if (isSelected) {

                            Text(
                                text = "SELECTED",
                                color = brightBlue,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // -----------------------------------------------------
        // SELECTED PRODUCTS
        // -----------------------------------------------------

        if (selectedProducts.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "STORE ASSESSMENT",
                        color = white,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "${selectedProducts.size} product(s) selected",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = "ASSESS",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            selectedProducts.forEachIndexed { index, product ->

                ProductAssessmentCard(
                    product = product,
                    cardColor = cardBlack,
                    elevatedBlack = elevatedBlack,
                    white = white,
                    secondaryText = secondaryText,
                    brightBlue = brightBlue,
                    green = green,
                    red = red,
                    borderColor = borderColor,
                    onProductChanged = { updatedProduct ->

                        selectedProducts[index] =
                            updatedProduct
                    }
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // -----------------------------------------------------
        // CONTINUE
        // -----------------------------------------------------

        Button(
            onClick = {

                businessData.products =
                    selectedProducts.toList()

                // Keep the older single-product
                // fields updated for compatibility.

                if (selectedProducts.isNotEmpty()) {

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
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = white
            )
        ) {

            Text(
                text = "CONTINUE  →",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}


// =============================================================
// PRODUCT ASSESSMENT CARD
// =============================================================

@Composable
private fun ProductAssessmentCard(
    product: ProductAssessment,
    cardColor: Color,
    elevatedBlack: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    green: Color,
    red: Color,
    borderColor: Color,
    onProductChanged: (ProductAssessment) -> Unit
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(18.dp)
    ) {

        // -----------------------------------------------------
        // PRODUCT HEADER
        // -----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.productName,
                    color = white,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = product.productCategory,
                    color = brightBlue,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // -----------------------------------------------------
        // AVAILABILITY
        // -----------------------------------------------------

        Text(
            text = "AVAILABILITY",
            color = white,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(elevatedBlack)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected =
                    product.availability ==
                            "Present",

                onClick = {

                    onProductChanged(
                        product.copy(
                            availability = "Present"
                        )
                    )
                },

                colors = RadioButtonDefaults.colors(
                    selectedColor = green,
                    unselectedColor = secondaryText
                )
            )

            Text(
                text = "Present",
                color = white
            )

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            RadioButton(
                selected =
                    product.availability ==
                            "Missing",

                onClick = {

                    onProductChanged(
                        product.copy(
                            availability = "Missing"
                        )
                    )
                },

                colors = RadioButtonDefaults.colors(
                    selectedColor = red,
                    unselectedColor = secondaryText
                )
            )

            Text(
                text = "Missing",
                color = white
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // -----------------------------------------------------
        // QUANTITY
        // -----------------------------------------------------

        ProductTextField(
            value = quantity,
            onValueChange = { value ->

                quantity = value

                onProductChanged(
                    product.copy(
                        quantity = value
                    )
                )
            },
            label = "Quantity",
            white = white,
            secondaryText = secondaryText,
            brightBlue = brightBlue,
            borderColor = borderColor
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // -----------------------------------------------------
        // PRICE
        // -----------------------------------------------------

        ProductTextField(
            value = price,
            onValueChange = { value ->

                price = value

                onProductChanged(
                    product.copy(
                        price = value
                    )
                )
            },
            label = "Price",
            white = white,
            secondaryText = secondaryText,
            brightBlue = brightBlue,
            borderColor = borderColor
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // -----------------------------------------------------
        // NOTES
        // -----------------------------------------------------

        OutlinedTextField(
            value = notes,
            onValueChange = { value ->

                notes = value

                onProductChanged(
                    product.copy(
                        notes = value
                    )
                )
            },
            label = {
                Text(
                    text = "Notes",
                    color = secondaryText
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedTextColor = white,
                unfocusedTextColor = white,
                focusedBorderColor = brightBlue,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = brightBlue,
                unfocusedLabelColor = secondaryText,
                cursorColor = brightBlue
            )
        )
    }
}


// =============================================================
// TEXT FIELD
// =============================================================

@Composable
private fun ProductTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    borderColor: Color
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = secondaryText
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            focusedTextColor = white,
            unfocusedTextColor = white,
            focusedBorderColor = brightBlue,
            unfocusedBorderColor = borderColor,
            focusedLabelColor = brightBlue,
            unfocusedLabelColor = secondaryText,
            cursorColor = brightBlue
        )
    )
}


// =============================================================
// PROGRESS
// =============================================================

@Composable
private fun ProductProgressStep(
    active: Boolean,
    modifier: Modifier,
    brightBlue: Color
) {

    Spacer(
        modifier = modifier
            .height(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (active) {
                    brightBlue
                } else {
                    Color(0xFF28313A)
                }
            )
    )
}
