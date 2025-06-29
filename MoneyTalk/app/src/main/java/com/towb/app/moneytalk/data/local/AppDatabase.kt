package com.towb.app.moneytalk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.towb.app.moneytalk.data.model.EventItem
import com.towb.app.moneytalk.utils.DateTimeConverter

@Database(
    entities = [EventItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventItemDao(): EventItemDao
}
