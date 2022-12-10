package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.repository.HabitRepositoryImpl
import com.depromeet.threedays.data.repository.NotificationRepositoryImpl
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.repository.NotificationRepository
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
        repository: HabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindsNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository
}
