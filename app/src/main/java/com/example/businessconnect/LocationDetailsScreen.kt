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
fun LocationDetailsScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var streetAddress by remember {
        mutableStateOf(businessData.streetAddress)
    }

    var city by remember {
        mutableStateOf(businessData.city)
    }

    var province by remember {
        mutableStateOf(businessData.province)
    }

    var postalCode by remember {
        mutableStateOf(businessData.postalCode)
    }

    var latitude by remember {
        mutableStateOf(businessData.latitude)
    }

    var longitude by remember {
        mutableStateOf(businessData.longitude)
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
            text = "Location Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 3 of 4 • Business Location",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = streetAddress,
            onValueChange = {
                streetAddress = it
            },
            label = {
                Text("Street Address")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = city,
            onValueChange = {
                city = it
            },
            label = {
                Text("City")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = province,
            onValueChange = {
                province = it
            },
            label = {
                Text("Province")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = postalCode,
            onValueChange = {
                postalCode = it
            },
            label = {
                Text("Postal Code")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = latitude,
            onValueChange = {
                latitude = it
            },
            label = {
                Text("Latitude")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = longitude,
            onValueChange = {
                longitude = it
            },
            label = {
                Text("Longitude")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                businessData.streetAddress = streetAddress
                businessData.city = city
                businessData.province = province
                businessData.postalCode = postalCode
                businessData.latitude = latitude
                businessData.longitude = longitude

                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}