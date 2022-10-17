package com.depromeet.threedays.domain.entity

data class Goal(
    val title: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val notificationTime: String,
    val notificationContent: String,
    val status: String,
    val create_date: String,
    val sequence: String,
    val lastAchievementDate: String
)
