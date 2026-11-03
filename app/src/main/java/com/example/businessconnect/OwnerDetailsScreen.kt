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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OwnerDetailsScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val primaryBlue = Color(0xFF0066CC)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)

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
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        // Back button
        TextButton(
            onClick = onBack
        ) {
            Text(
                text = "←  BACK",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Header
        Text(
            text = "OWNER DETAILS",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Capture the primary business contact",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Progress
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "OWNER / CONTACT",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "02 / 04",
                    color = secondaryText,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                ProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProgressStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                ProgressStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Contact information card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Text(
                text = "CONTACT INFORMATION",
                color = white,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Enter the details of the owner or main contact.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(18.dp))

            OwnerTextField(
                value = ownerName,
                onValueChange = {
                    ownerName = it
                },
                label = "Owner Full Name",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            OwnerTextField(
                value = contactPerson,
                onValueChange = {
                    contactPerson = it
                },
                label = "Contact Person",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            OwnerTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                label = "Phone Number",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            OwnerTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = "Email Address",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Continue button
        Button(
            onClick = {

                businessData.ownerName = ownerName
                businessData.contactPerson = contactPerson
                businessData.ownerPhone = phoneNumber
                businessData.ownerEmail = email

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

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun OwnerTextField(
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

@Composable
private fun ProgressStep(
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
