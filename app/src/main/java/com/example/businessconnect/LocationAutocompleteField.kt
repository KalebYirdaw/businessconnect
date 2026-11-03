package com.example.businessconnect

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Reusable "start typing and pick a place" field (Photon / OpenStreetMap).
 * Uses the surrounding MaterialTheme colours, so it fits any screen.
 */
@Composable
fun LocationAutocompleteField(
    onLocationSelected: (LocationSearchResult) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Search location",
    placeholder: String = "Start typing a place or address"
) {
    var text by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<LocationSuggestion>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var noResults by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf<String?>(null) }
    var hasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(text) {
        if (text == selectedText) return@LaunchedEffect
        noResults = false
        if (text.trim().length < 2) {
            suggestions = emptyList()
            isSearching = false
            return@LaunchedEffect
        }
        delay(300) // debounce; a new keystroke cancels this effect
        isSearching = true
        val found = withContext(Dispatchers.IO) { LocationSearchService.suggest(text) }
        suggestions = found
        noResults = found.isEmpty()
        isSearching = false
    }

    Column(modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                if (isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else if (text.isNotEmpty()) {
                    TextButton(onClick = {
                        text = ""
                        suggestions = emptyList()
                    }) { Text("✕") }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { hasFocus = it.isFocused },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
            shape = RoundedCornerShape(10.dp)
        )

        if (suggestions.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp))
            ) {
                suggestions.forEachIndexed { index, s ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val result = LocationSearchService.resolve(s)
                                if (result != null) {
                                    focusManager.clearFocus()
                                    onLocationSelected(result)
                                    selectedText = s.title
                                    text = s.title
                                    suggestions = emptyList()
                                }
                            }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(
                            s.title,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (s.subtitle.isNotBlank()) {
                            Text(
                                s.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    if (index < suggestions.lastIndex) HorizontalDivider()
                }
            }
        } else if (noResults && hasFocus) {
            Text(
                "No matches. Keep typing or fill the fields in manually below.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 6.dp, start = 4.dp)
            )
        }

        Spacer(Modifier.height(10.dp))
    }
}
