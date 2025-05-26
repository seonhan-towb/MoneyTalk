package com.towb.app.moneytalk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "event_items")
data class EventItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val eventTitle: String,          // 소비 품목 (예: 커피)
    val eventPrice: Int,             // 가격
    val eventCategory: String,       // 카테고리 (예: 식비, 교통 등)
    val eventTime: LocalTime,        // 시간 (예: 14:30)
    val eventDate: LocalDate         // 날짜 (예: 2025-05-26)
)
