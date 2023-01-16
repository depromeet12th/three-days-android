package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.api.AchievementService
import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.base.getResult
import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.domain.exception.ThreeDaysException
import java.time.LocalDate
import javax.inject.Inject

class AchievementRemoteDataSourceImpl @Inject constructor(
    private val achievementService: AchievementService
): AchievementRemoteDataSource {
    override suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): Result<List<AchievementEntity>> {
        return achievementService.getHabitAchievements(habitId = habitId, from = from, to = to).getResult() ?: Result.success(emptyList())
    }

    override suspend fun postHabitAchievement(
        habitId: Long,
        achievementDateEntity: AchievementDateEntity
    ): Result<HabitEntity> {
        return achievementService.postHabitAchievement(habitId = habitId, request = achievementDateEntity).getResult()
            ?: Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
    }

    override suspend fun deleteHabitAchievement(
        habitId: Long,
        habitAchievementId: Long,
    ): Result<HabitEntity> {
        return achievementService.deleteHabitAchievement(habitId = habitId, habitAchievementId = habitAchievementId).getResult()
            ?: Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
    }
}
