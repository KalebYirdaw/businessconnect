package com.example.businessconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.businessconnect.ui.theme.BusinessConnectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BusinessConnectTheme {
                BusinessConnectApp()
            }
        }
    }
}

@Composable
fun BusinessConnectApp() {

    var currentScreen by remember {
        mutableStateOf("dashboard")
    }

    val businessData = remember {
        BusinessData()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        when (currentScreen) {

            "dashboard" -> {

                DashboardScreen(
                    modifier = Modifier.padding(innerPadding),
                    onAddBusiness = {
                        currentScreen = "addBusiness"
                    }
                )
            }

            "addBusiness" -> {

                AddBusinessScreen(
                    businessData = businessData,
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        currentScreen = "dashboard"
                    },
                    onNext = {
                        currentScreen = "ownerDetails"
                    }
                )
            }

            "ownerDetails" -> {

                OwnerDetailsScreen(
                    businessData = businessData,
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        currentScreen = "addBusiness"
                    },
                    onNext = {
                        currentScreen = "locationDetails"
                    }
                )
            }

            "locationDetails" -> {

                LocationDetailsScreen(
                    businessData = businessData,
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        currentScreen = "ownerDetails"
                    },
                    onNext = {
                        currentScreen = "products"
                    }
                )
            }

            "products" -> {

                ProductsScreen(
                    businessData = businessData,
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        currentScreen = "locationDetails"
                    },
                    onNext = {
                        currentScreen = "review"
                    }
                )
            }

            "review" -> {

                ReviewScreen(
                    businessData = businessData,
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        currentScreen = "products"
                    },
                    onSave = {
                        currentScreen = "dashboard"
                    }
                )
            }
        }
    }
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onAddBusiness: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "BusinessConnect",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Manage your business customers",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onAddBusiness,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Business")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                // We will build this later
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Businesses")
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OverviewCard(
            title = "Businesses",
            description = "Stores and business customers"
        )

        Spacer(modifier = Modifier.height(12.dp))

        OverviewCard(
            title = "Locations",
            description = "Business store locations"
        )

        Spacer(modifier = Modifier.height(12.dp))

        OverviewCard(
            title = "Products",
            description = "Products supplied to businesses"
        )
    }
}

@Composable
fun OverviewCard(
    title: String,
    description: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}