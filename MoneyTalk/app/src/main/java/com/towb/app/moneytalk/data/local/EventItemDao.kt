package com.towb.app.moneytalk.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towb.app.moneytalk.data.model.EventItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EventItem)

    @Delete
    suspend fun delete(item: EventItem)

    @Query("SELECT * FROM event_items ORDER BY eventDate DESC, eventTime DESC")
    fun getAll(): Flow<List<EventItem>>

    @Query("SELECT * FROM event_items WHERE eventDate = :date ORDER BY eventTime ASC")
    fun getEventsByDate(date: LocalDate): Flow<List<EventItem>>

}