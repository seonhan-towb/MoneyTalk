package com.towb.app.moneytalk.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.towb.app.moneytalk.data.model.EventItem
import com.towb.app.moneytalk.utils.PriceFormatter
import java.time.LocalDate

@Composable
fun TimeTableView(
    date: LocalDate,
    timeTable: List<EventItem>,
    onAddEventClick: () -> Unit = {},
    onDeleteEvent: (EventItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일",
                style = MaterialTheme.typography.headlineSmall,
            )

            Button(
                onClick = onAddEventClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("추가")
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                items(timeTable) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.eventTitle,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "${PriceFormatter.format(item.eventPrice)} · ${item.eventTime}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            IconButton(onClick = { onDeleteEvent(item) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "삭제")
                            }
                        }
                    }
                }
            }
        }
    }
}