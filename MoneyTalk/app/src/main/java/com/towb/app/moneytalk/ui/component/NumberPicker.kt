package com.towb.app.moneytalk.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NumberPicker(value: Int, onValueChange: (Int) -> Unit, range: IntRange) {
    Row {
        IconButton(onClick = { if (value > range.first) onValueChange(value - 1) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
        }
        Text(text = "$value")
        IconButton(onClick = { if (value < range.last) onValueChange(value + 1) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
        }
    }
}