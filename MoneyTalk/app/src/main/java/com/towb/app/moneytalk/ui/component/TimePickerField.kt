package com.towb.app.moneytalk.ui.component

import android.app.TimePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePickerField(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val timeText = selectedTime.format(timeFormatter)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("시간", modifier = Modifier.padding(bottom = 4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (!isInPreview) {
                        TimePickerDialog(
                            context, { _, hour, minute ->
                                onTimeSelected(LocalTime.of(hour, minute))
                            },
                            selectedTime.hour,
                            selectedTime.minute,
                            true
                        ).show()
                    }
                }
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = timeText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

