package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.datasource.achievement.AchievementRemoteDataSource
import com.depromeet.threedays.data.datasource.achievement.AchievementRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.auth.AuthRemoteDataSource
import com.depromeet.threedays.data.datasource.auth.AuthRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSource
import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.mate.MateRemoteDataSource
import com.depromeet.threedays.data.datasource.mate.MateRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.member.MemberRemoteDataSource
import com.depromeet.threedays.data.datasource.member.MemberRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.notification.NotificationHistoryRemoteDataSource
import com.depromeet.threedays.data.datasource.notification.NotificationHistoryRemoteDataSourceImpl
import com.depromeet.threedays.data.datasource.record.RecordRemoteDataSource
import com.depromeet.threedays.data.datasource.record.RecordRemoteDataSourceImpl
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
        dataSource: HabitRemoteDataSourceImpl,
    ): HabitRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationHistoryRemoteDataSource(
        dataSource: NotificationHistoryRemoteDataSourceImpl,
    ): NotificationHistoryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMateRemoteDataSource(
        dataSource: MateRemoteDataSourceImpl
    ): MateRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAchievementRemoteDataSource(
        dataSource: AchievementRemoteDataSourceImpl
    ): AchievementRemoteDataSource
    
    @Binds
    @Singleton
    abstract fun bindMemberRemoteDataSource(
        dataSource: MemberRemoteDataSourceImpl,
    ): MemberRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        dataSource: AuthRemoteDataSourceImpl,
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRecordRemoteDataSource(
        dataSource: RecordRemoteDataSourceImpl,
    ): RecordRemoteDataSource
}
