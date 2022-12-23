package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.achievement.Achievement
import java.time.LocalDate

interface AchievementRepository {
    suspend fun getHabitAchievements(habitId: Long, from: LocalDate, to: LocalDate): List<Achievement>
}
