package com.towb.app.moneytalk.ui.datavisualizer

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towb.app.moneytalk.data.model.ConsumptionCategory
import com.towb.app.moneytalk.data.model.ConsumptionData
import com.towb.app.moneytalk.data.model.StatisticsType
import com.towb.app.moneytalk.data.remote.EventItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: EventItemRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _statisticsData = MutableStateFlow<ConsumptionData?>(null)
    val statisticsData = _statisticsData.asStateFlow()

    private fun getColorForCategory(category: String): Color = when (category) {
        "식비" -> Color(0xFFF8C8C8)
        "문화" -> Color(0xFFB9AEE5)
        "교통" -> Color(0xFFA8DADB)
        "기타" -> Color(0xFFBFC4CC)
        else -> Color.Gray
    }

    fun formatStatisticsTitle(date: LocalDate, type: StatisticsType): String {
        return when (type) {
            StatisticsType.Daily -> "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 기준"
            StatisticsType.Monthly -> "${date.year}년 ${date.monthValue}월 기준"
            StatisticsType.Yearly -> "${date.year}년 기준"
        }
    }

    fun loadStatisticsData(date: LocalDate, type: StatisticsType) {
        viewModelScope.launch {
            _title.value = formatStatisticsTitle(date, type)

            val summaries = when (type) {
                StatisticsType.Daily -> repository.getDailySummary(date)
                StatisticsType.Monthly -> repository.getMonthlySummary(date)
                StatisticsType.Yearly -> repository.getYearlySummary(date)
            }

            val total = summaries.sumOf { it.totalPrice }

            val categories = summaries.map {
                val percent = if (total == 0) 0 else (it.totalPrice * 100) / total
                ConsumptionCategory(
                    name = it.eventCategory,
                    percentage = percent,
                    color = getColorForCategory(it.eventCategory)
                )
            }

            _statisticsData.value = ConsumptionData(
                totalAmount = total.toString(),
                categories = categories
            )
        }
    }
}
