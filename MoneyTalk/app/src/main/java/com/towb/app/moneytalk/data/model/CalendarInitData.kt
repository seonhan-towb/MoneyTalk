package com.towb.app.moneytalk.data.model

import java.time.DayOfWeek
import java.util.Locale

/**
 * 캘린더 초기화에 필요한 데이터를 담는 data class
 */
data class CalendarInitData(
    val yearRange: IntRange = 1970..2100,
    val locale: Locale = Locale.getDefault(),
    val firstDayOfWeek: Int = DayOfWeek.SUNDAY.value - 1 // 0: Sunday, 6: Saturday
) {
    init {
        require(yearRange.first in 1900..2100 && yearRange.last in 1900..2100) {
            "Year range must be within 1900 to 2100"
        }
    }
}
