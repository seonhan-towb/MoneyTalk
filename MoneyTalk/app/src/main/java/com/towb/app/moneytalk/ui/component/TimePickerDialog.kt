package com.towb.app.moneytalk.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalTime

@Composable
fun TimePickerDialog(onTimeSelected: (LocalTime) -> Unit) {
    val hour = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("시간 선택") },
        text = {
            Column {
                Text("시간을 선택하세요")
                Row {
                    NumberPicker(value = hour.value, onValueChange = { hour.value = it }, range = 0..23)
                    Text(":")
                    NumberPicker(value = minute.value, onValueChange = { minute.value = it }, range = 0..59)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(LocalTime.of(hour.value, minute.value))
                }
            ) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = { /* Do nothing */ }) {
                Text("취소")
            }
        }
    )
}
