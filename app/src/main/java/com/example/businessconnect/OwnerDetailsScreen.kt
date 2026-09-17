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
fun OwnerDetailsScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var ownerName by remember {
        mutableStateOf(businessData.ownerName)
    }

    var contactPerson by remember {
        mutableStateOf(businessData.contactPerson)
    }

    var phoneNumber by remember {
        mutableStateOf(businessData.ownerPhone)
    }

    var email by remember {
        mutableStateOf(businessData.ownerEmail)
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
            text = "Owner Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 2 of 4 • Owner / Contact Details",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = ownerName,
            onValueChange = {
                ownerName = it
            },
            label = {
                Text("Owner Full Name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contactPerson,
            onValueChange = {
                contactPerson = it
            },
            label = {
                Text("Contact Person")
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

                businessData.ownerName = ownerName
                businessData.contactPerson = contactPerson
                businessData.ownerPhone = phoneNumber
                businessData.ownerEmail = email

                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}