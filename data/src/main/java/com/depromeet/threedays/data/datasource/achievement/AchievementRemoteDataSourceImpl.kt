package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.api.AchievementService
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import java.time.LocalDate
import javax.inject.Inject

class AchievementRemoteDataSourceImpl @Inject constructor(
    private val achievementService: AchievementService
): AchievementRemoteDataSource {
    override suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<AchievementEntity> {
        return achievementService.getHabitAchievements(habitId = habitId, from = from, to = to).data ?: emptyList()
    }
}
