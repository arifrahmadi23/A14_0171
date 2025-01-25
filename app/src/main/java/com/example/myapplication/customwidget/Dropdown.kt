package com.example.myapplication.customwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label : String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedItem) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            enabled = enabled,
            readOnly = true,
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}