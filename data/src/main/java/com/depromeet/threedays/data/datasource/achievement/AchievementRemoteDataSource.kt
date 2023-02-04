package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.habit.HabitEntity
import java.time.LocalDate

interface AchievementRemoteDataSource {
    suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): Result<List<AchievementEntity>>
    suspend fun postHabitAchievement(habitId: Long, achievementDateEntity: AchievementDateEntity): Result<HabitEntity>
    suspend fun deleteHabitAchievement(habitId: Long, habitAchievementId: Long): Result<HabitEntity>
}
