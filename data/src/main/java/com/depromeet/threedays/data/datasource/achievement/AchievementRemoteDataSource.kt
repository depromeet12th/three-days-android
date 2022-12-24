package com.depromeet.threedays.data.datasource.achievement

import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.habit.HabitEntity
import java.time.LocalDate

interface AchievementRemoteDataSource {
    suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<AchievementEntity>
    suspend fun postHabitAchievement(habitId: Long, request: AchievementDateEntity): ApiResponse<HabitEntity>
    suspend fun deleteHabitAchievement(habitId: Long, habitAchievementId: Long): ApiResponse<HabitEntity>
}
