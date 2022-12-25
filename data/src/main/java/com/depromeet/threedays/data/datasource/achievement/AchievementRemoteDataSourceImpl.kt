package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.api.AchievementService
import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.habit.HabitEntity
import java.time.LocalDate
import javax.inject.Inject

class AchievementRemoteDataSourceImpl @Inject constructor(
    private val achievementService: AchievementService
): AchievementRemoteDataSource {
    override suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<AchievementEntity> {
        return achievementService.getHabitAchievements(habitId = habitId, from = from, to = to).data ?: emptyList()
    }

    override suspend fun postHabitAchievement(
        habitId: Long,
        request: AchievementDateEntity
    ): ApiResponse<HabitEntity> {
        return achievementService.postHabitAchievement(habitId = habitId, request = request)
    }

    override suspend fun deleteHabitAchievement(
        habitId: Long,
        habitAchievementId: Long,
    ): ApiResponse<HabitEntity> {
        return achievementService.deleteHabitAchievement(habitId = habitId, habitAchievementId = habitAchievementId)
    }
}
