package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.repository.LocalGoalRepository
import com.depromeet.threedays.domain.repository.GoalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalGoalRepository(
        goalDataSource: GoalDataSource
    ): GoalRepository {
        return LocalGoalRepository(goalDataSource)
    }
}
