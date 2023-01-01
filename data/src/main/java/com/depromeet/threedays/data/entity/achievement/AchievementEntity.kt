package com.depromeet.threedays.data.entity.achievement

import java.time.LocalDate

data class AchievementEntity (
    val achievementDate: LocalDate,
    val habitId: Long,
    val id: Long,
    val nextAchievementDate: LocalDate,
    val sequence: Int
)
