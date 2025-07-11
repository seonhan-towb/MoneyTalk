package com.towb.app.moneytalk.data.remote

import com.towb.app.moneytalk.data.local.EventItemDao
import com.towb.app.moneytalk.data.model.EventItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventItemRepository (
    private val dao: EventItemDao
) {
    suspend fun insert(item: EventItem) = dao.insert(item)
    suspend fun delete(item: EventItem) = dao.delete(item)
    fun getAll(): Flow<List<EventItem>> = dao.getAll()

    fun getTodayEventItems(date: LocalDate): Flow<List<EventItem>> = dao.getEventsByDate(date)

    suspend fun getDailySummary(date: LocalDate) = dao.getDailySummary(date)

    suspend fun getMonthlySummary(date: LocalDate) = dao.getMonthlySummary(date)

    suspend fun getYearlySummary(date: LocalDate) = dao.getYearlySummary(date)
}