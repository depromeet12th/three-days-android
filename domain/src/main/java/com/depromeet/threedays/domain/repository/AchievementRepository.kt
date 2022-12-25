package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.achievement.Achievement
import com.depromeet.threedays.domain.entity.habit.Habit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AchievementRepository {
    suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<Achievement>
    fun createHabitAchievement(habitId: Long): Flow<DataState<Habit>>
    fun deleteHabitAchievement(habitId: Long, habitAchievementId: Long): Flow<DataState<Habit>>
}
