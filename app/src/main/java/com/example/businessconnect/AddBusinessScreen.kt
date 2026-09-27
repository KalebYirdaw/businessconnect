
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun AddBusinessScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val elevatedBlack = Color(0xFF171C22)
    val primaryBlue = Color(0xFF0066CC)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)

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

    var storeStatus by remember {
        mutableStateOf(businessData.storeStatus)
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
            onClick = onBack,
            modifier = Modifier.padding(start = 0.dp)
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
            text = "ADD BUSINESS",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Capture a new business opportunity",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Progress indicator
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
                    text = "BUSINESS DETAILS",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "01 / 04",
                    color = secondaryText,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                BoxStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    activeColor = brightBlue
                )

                BoxStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    activeColor = brightBlue
                )

                BoxStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    activeColor = brightBlue
                )

                BoxStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    activeColor = brightBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Business information card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Text(
                text = "BUSINESS INFORMATION",
                color = white,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Enter the basic details of the business.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(18.dp))

            BusinessTextField(
                value = businessName,
                onValueChange = { businessName = it },
                label = "Business / Store Name",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            BusinessTextField(
                value = businessType,
                onValueChange = { businessType = it },
                label = "Business Type",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            BusinessTextField(
                value = registrationNumber,
                onValueChange = { registrationNumber = it },
                label = "Registration Number",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            BusinessTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            BusinessTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Store status card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Text(
                text = "STORE STATUS",
                color = white,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Select the current status of this business.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            StatusOption(
                selected = storeStatus == "New Prospect",
                title = "New Prospect",
                description = "A new potential business opportunity",
                onClick = {
                    storeStatus = "New Prospect"
                },
                cardColor = elevatedBlack,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue
            )

            Spacer(modifier = Modifier.height(10.dp))

            StatusOption(
                selected = storeStatus == "Existing Store",
                title = "Existing Store",
                description = "An existing business or retail location",
                onClick = {
                    storeStatus = "Existing Store"
                },
                cardColor = elevatedBlack,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Next button
        Button(
            onClick = {

                businessData.businessName = businessName
                businessData.businessType = businessType
                businessData.registrationNumber = registrationNumber
                businessData.businessPhone = phoneNumber
                businessData.businessEmail = email
                businessData.storeStatus = storeStatus

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
private fun BusinessTextField(
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
private fun StatusOption(
    selected: Boolean,
    title: String,
    description: String,
    onClick: () -> Unit,
    cardColor: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .border(
                width = 1.dp,
                color = if (selected) brightBlue else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = brightBlue,
                unselectedColor = secondaryText
            )
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {

            Text(
                text = title,
                color = white,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun BoxStep(
    active: Boolean,
    modifier: Modifier,
    activeColor: Color
) {

    Spacer(
        modifier = modifier
            .height(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (active) activeColor
                else Color(0xFF28313A)
            )
    )
}
