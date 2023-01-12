package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.achievement.AchievementRemoteDataSource
import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.mapper.toAchievement
import com.depromeet.threedays.data.mapper.toHabit
import com.depromeet.threedays.domain.entity.achievement.Achievement
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val achievementRemoteDataSource: AchievementRemoteDataSource
): AchievementRepository {
    override suspend fun getHabitAchievements(
        habitId: Long,
        from: LocalDate,
        to: LocalDate
    ): Result<List<Achievement>> {
        return achievementRemoteDataSource.getHabitAchievements(habitId = habitId, from = from, to = to).map { list -> list.map { it.toAchievement() } }
    }

    override fun createHabitAchievement(habitId: Long): Flow<Result<Habit>> = flow {
        val localDate = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate()

        achievementRemoteDataSource.postHabitAchievement(
            habitId  = habitId,
            achievementDateEntity = AchievementDateEntity(localDate),
        ).onSuccess { response ->
            emit(Result.success(value =  response.toHabit()))
        }.onFailure { throwable ->
            throwable as ThreeDaysException
            emit(Result.failure(throwable))
        }
    }

    override fun deleteHabitAchievement(
        habitId: Long,
        habitAchievementId: Long
    ): Flow<Result<Habit>> = flow {
        achievementRemoteDataSource.deleteHabitAchievement(
            habitId = habitId,
            habitAchievementId = habitAchievementId
        ).onSuccess { response ->
            emit(Result.success(value =  response.toHabit()))
        }.onFailure { throwable ->
            throwable as ThreeDaysException
            emit(Result.failure(throwable))
        }
    }
}
