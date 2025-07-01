@file:OptIn(ExperimentalMaterial3Api::class)

package com.towb.app.moneytalk.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.towb.app.moneytalk.data.model.EventItem
import com.towb.app.moneytalk.ui.component.CategoryDropdown
import com.towb.app.moneytalk.ui.component.TimePickerField
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun AddEventScreen(
    selectedDate: LocalDate,
    onSave: (EventItem) -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var time by remember {
        mutableStateOf(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
    }

    val categoryOptions = listOf("식비", "교통", "문화", "기타")
    var selectedCategory by remember { mutableStateOf("식비") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("소비 추가") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onSave(
                        EventItem(
                            eventTitle = title,
                            eventPrice = price.toIntOrNull() ?: 0,
                            eventCategory = selectedCategory,
                            eventTime = time,
                            eventDate = selectedDate
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("저장")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryDropdown(
                selectedCategory = selectedCategory,
                options = categoryOptions,
                onCategorySelected = { selectedCategory = it }
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("소비 항목") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = {
                    if (it.all(Char::isDigit)) price = it
                },
                label = { Text("가격 (숫자)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TimePickerField(selectedTime = time) {
                time = it
            }
        }
    }
}
