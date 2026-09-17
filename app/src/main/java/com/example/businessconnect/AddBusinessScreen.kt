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
fun AddBusinessScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var businessName by remember {
        mutableStateOf(businessData.businessName)
    }

    var businessType by remember {
        mutableStateOf(businessData.businessType)
    }

    var registrationNumber by remember {
        mutableStateOf(businessData.registrationNumber)
    }

    var phoneNumber by remember {
        mutableStateOf(businessData.businessPhone)
    }

    var email by remember {
        mutableStateOf(businessData.businessEmail)
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
            text = "Add Business",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 1 of 4 • Business Details",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = businessName,
            onValueChange = {
                businessName = it
            },
            label = {
                Text("Business / Store Name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = businessType,
            onValueChange = {
                businessType = it
            },
            label = {
                Text("Business Type")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationNumber,
            onValueChange = {
                registrationNumber = it
            },
            label = {
                Text("Registration Number")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            label = {
                Text("Phone Number")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email Address")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                businessData.businessName = businessName
                businessData.businessType = businessType
                businessData.registrationNumber = registrationNumber
                businessData.businessPhone = phoneNumber
                businessData.businessEmail = email

                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}