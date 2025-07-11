package com.towb.app.moneytalk.ui.datavisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.towb.app.moneytalk.data.model.ConsumptionCategory
import com.towb.app.moneytalk.data.model.ConsumptionData
import com.towb.app.moneytalk.data.model.StatisticsType
import com.towb.app.moneytalk.utils.PriceFormatter
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataVisualizerScreen(
    selectedDate: LocalDate,
    onCancel: () -> Unit = {},
) {
    val statisticsViewModel: StatisticsViewModel = hiltViewModel()
    val statisticsData by statisticsViewModel.statisticsData.collectAsState()
    val title by statisticsViewModel.title.collectAsState()

    var selectedTab by remember { mutableStateOf("일간") }

    LaunchedEffect(selectedTab, selectedDate) {
        statisticsViewModel.loadStatisticsData(
            date = selectedDate,
            type = when (selectedTab) {
                "일간" -> StatisticsType.Daily
                "월간" -> StatisticsType.Monthly
                "연간" -> StatisticsType.Yearly
                else -> StatisticsType.Daily
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("소비 통계") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding).padding(8.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatisticsTabRow(
                title = title,
                selectedTab = selectedTab
            ) {
                selectedTab = it
            }

            statisticsData?.let { data ->
                StatisticsContent(data = data)
            }
        }
    }
}

@Composable
fun StatisticsTabRow(
    title: String,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val tabs = listOf("일간", "월간", "연간")
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(shape)
                .background(Color(0xFFF1F3F6))
        ) {
            tabs.forEach { tab ->
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(tab) }
                        .background(
                            if (isSelected) Color(0xFF344B7E) else Color.Transparent,
                            shape = shape
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (isSelected) Color.White else Color(0xFF333333),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = title,
            modifier = Modifier
                .padding(top = 8.dp, start = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun StatisticsContent(data: ConsumptionData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DonutChart(data.categories)

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                data.categories.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Canvas(modifier = Modifier.size(10.dp)) {
                            drawCircle(color = it.color)
                        }
                        Spacer(Modifier.width(8.dp))

                        Text(text = it.name)
                        Spacer(Modifier.width(8.dp))

                        Text(text = "${it.percentage}%", textAlign = TextAlign.End)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "총 소비 ${PriceFormatter.format(data.totalAmount.toInt())}",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun DonutChart(categories: List<ConsumptionCategory>) {
    val total = categories.fold(0f) { acc, category -> acc + category.percentage.toFloat() }

    Canvas(modifier = Modifier.size(180.dp)) {
        var startAngle = -90f
        categories.forEach { category ->
            val sweepAngle = (category.percentage / total) * 360f
            drawArc(
                color = category.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 90f, cap = StrokeCap.Butt)
            )
            startAngle += sweepAngle
        }
    }
}
