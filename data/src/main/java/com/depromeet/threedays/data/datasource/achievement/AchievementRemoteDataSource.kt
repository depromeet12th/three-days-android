package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import java.time.LocalDate

interface AchievementRemoteDataSource {
    suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<AchievementEntity>
}
