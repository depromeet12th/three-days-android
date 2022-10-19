package com.depromeet.threedays.data.di

import android.content.Context
import androidx.room.Room
import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.room.DATABASE_NAME_THREE_DAYS
import com.depromeet.threedays.data.room.ThreeDaysDatabase
import com.depromeet.threedays.data.room.goal.GoalDao
import com.depromeet.threedays.data.room.goal.LocalGoalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): ThreeDaysDatabase {
        return Room.databaseBuilder(
            context,
            ThreeDaysDatabase::class.java,
            DATABASE_NAME_THREE_DAYS
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideGoalDao(threeDaysDatabase: ThreeDaysDatabase): GoalDao {
        return threeDaysDatabase.goalDao()
    }

    @Provides
    @Singleton
    fun provideGoalDataSource(goalDao: GoalDao): GoalDataSource {
        return LocalGoalDataSource(goalDao)
    }
}
