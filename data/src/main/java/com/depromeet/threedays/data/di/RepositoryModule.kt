package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.repository.HabitRepositoryImpl
import com.depromeet.threedays.data.repository.NotificationHistoryRepositoryImpl
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.repository.NotificationHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsHabitRepository(
        repository: HabitRepositoryImpl,
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindsNotificationHistoryRepository(
        repository: NotificationHistoryRepositoryImpl,
    ): NotificationHistoryRepository
}
