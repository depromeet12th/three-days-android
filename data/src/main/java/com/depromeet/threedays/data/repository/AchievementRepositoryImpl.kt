package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.achievement.AchievementRemoteDataSource
import com.depromeet.threedays.data.mapper.toAchievement
import com.depromeet.threedays.domain.entity.achievement.Achievement
import com.depromeet.threedays.domain.repository.AchievementRepository
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
}
