package com.example.businessconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    Column(

        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center

    ) {


        Text(

            text =
                "BusinessConnect",

            style =
                MaterialTheme.typography
                    .headlineLarge

        )


        Spacer(

            modifier =
                Modifier.height(12.dp)

        )


        Text(

            text =
                "Business Management Dashboard",

            style =
                MaterialTheme.typography
                    .bodyLarge

        )


        Spacer(

            modifier =
                Modifier.height(32.dp)

        )


        Button(

            onClick = {

                onAddBusiness()

            }

        ) {

            Text(
                "Add Business"
            )

        }


        Spacer(

            modifier =
                Modifier.height(16.dp)

        )


        Button(

            onClick = {

                onViewBusinesses()

            }

        ) {

            Text(
                "View Businesses"
            )

        }
    }
}