package com.example.matkul.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun halamanKomponen(){
    var showDialog by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    Column {
        Button(
            onClick = {},
            modifier = Modifier,
            enabled = true,
        ) {
            Text("Tes")
        }

        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier,
            enabled = true,
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Show Date Picker Dialog")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected date: ${(selectedDate)}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (showDialog) {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis
                            showDialog = false
                        }
                    ) {
                        Text("OK", color = MaterialTheme.colorScheme.primary)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel", color = MaterialTheme.colorScheme.primary)
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.sizeIn(maxWidth = 350.dp)
                )
            }
        }

    }
}
