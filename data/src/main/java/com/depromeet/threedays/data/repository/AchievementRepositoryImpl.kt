package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.achievement.AchievementRemoteDataSource
import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.mapper.toAchievement
import com.depromeet.threedays.data.mapper.toHabit
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.achievement.Achievement
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val achievementRemoteDataSource: AchievementRemoteDataSource
): AchievementRepository {
    override suspend fun getHabitAchievements(
        habitId: Long,
        from: LocalDate,
        to: LocalDate
    ): List<Achievement> {
        return achievementRemoteDataSource.getHabitAchievements(habitId = habitId, from = from, to = to).map { it.toAchievement() }
    }

    override fun createHabitAchievement(habitId: Long): Flow<DataState<Habit>> = flow {
        emit(DataState.loading())
        val response = achievementRemoteDataSource.postHabitAchievement(habitId, AchievementDateEntity("2022-12-25"))

        if (response.data != null) {
            emit(DataState.success(data = response.data.toHabit() ))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }

    override fun deleteHabitAchievement(
        habitId: Long,
        habitAchievementId: Long
    ): Flow<DataState<Habit>> = flow {
        emit(DataState.loading())
        val response = achievementRemoteDataSource.deleteHabitAchievement(
            habitId = habitId,
            habitAchievementId = habitAchievementId
        )

        if (response.data != null) {
            emit(DataState.success(data = response.data.toHabit() ))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }
}
