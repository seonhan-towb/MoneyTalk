package com.towb.app.moneytalk.data.model

enum class CalendarDayOfWeek {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    companion object {
        fun getWeekDays(firstDayOfWeek: Int): List<String> {
            val days = entries
            return (days.subList(firstDayOfWeek, days.size) + days.subList(0, firstDayOfWeek))
                .map { it.name.lowercase().replaceFirstChar { c -> c.titlecase() } }
        }
    }
}

enum class StatisticsType {
    Daily, Monthly, Yearly
}
