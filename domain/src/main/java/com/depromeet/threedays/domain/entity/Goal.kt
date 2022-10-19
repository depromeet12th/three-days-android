package com.depromeet.threedays.domain.entity

data class Goal(
    val goalId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val notificationTime: String,
    val notificationContent: String,
    val status: String,
    val createDate: String,
    val sequence: String,
    val lastAchievementDate: String
)
