package com.towb.app.moneytalk.data

import com.towb.app.moneytalk.data.local.EventItemDao
import com.towb.app.moneytalk.data.remote.EventItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideEventItemRepository(dao: EventItemDao): EventItemRepository {
        return EventItemRepository(dao)
    }
}