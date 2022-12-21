package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.repository.HabitRepositoryImpl
import com.depromeet.threedays.data.repository.MateRepositoryImpl
import com.depromeet.threedays.data.repository.MemberRepositoryImpl
import com.depromeet.threedays.data.repository.NotificationHistoryRepositoryImpl
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.repository.MateRepository
import com.depromeet.threedays.domain.repository.MemberRepository
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
    abstract fun bindsMateRepository(
        repository: MateRepositoryImpl
    ): MateRepository

    @Binds
    @Singleton
    abstract fun bindsNotificationHistoryRepository(
        repository: NotificationHistoryRepositoryImpl,
    ): NotificationHistoryRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(
        repository: MemberRepositoryImpl,
    ): MemberRepository
}
