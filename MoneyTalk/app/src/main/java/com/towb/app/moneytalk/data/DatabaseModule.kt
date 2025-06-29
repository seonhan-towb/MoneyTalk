package com.towb.app.moneytalk.data

import android.content.Context
import androidx.room.Room
import com.towb.app.moneytalk.data.local.AppDatabase
import com.towb.app.moneytalk.data.local.EventItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "moneytalk.db"
        ).build()
    }

    @Provides
    fun provideEventItemDao(
        db: AppDatabase
    ): EventItemDao {
        return db.eventItemDao()
    }
}