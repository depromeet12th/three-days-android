package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSource
import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.notification.NotificationHistoryRemoteDataSource
import com.depromeet.threedays.data.datasource.notification.NotificationHistoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindHabitRemoteDataSource(
        dataSource: HabitRemoteDataSourceImpl
    ): HabitRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationHistoryRemoteDataSource(
        dataSource: NotificationHistoryRemoteDataSourceImpl
    ): NotificationHistoryRemoteDataSource
}
