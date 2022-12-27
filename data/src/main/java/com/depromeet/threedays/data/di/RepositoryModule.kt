package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.repository.*
import com.depromeet.threedays.domain.repository.*
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
    abstract fun bindsNotificationPermissionRepository(
        repository: NotificationPermissionRepositoryImpl,
    ): NotificationPermissionRepository

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
    abstract fun bindsAchievementRepository(
        repository: AchievementRepositoryImpl,
    ): AchievementRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(
        repository: MemberRepositoryImpl,
    ): MemberRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(
        repository: AuthRepositoryImpl,
    ): AuthRepository
}
