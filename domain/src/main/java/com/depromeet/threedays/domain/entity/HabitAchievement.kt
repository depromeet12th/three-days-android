package com.depromeet.threedays.domain.entity

data class HabitAchievement(
    val habitAchievementId: Int,
    val sequence: Int,
    val achievementDate: String,
    val nextAchievementDate: String,
)
