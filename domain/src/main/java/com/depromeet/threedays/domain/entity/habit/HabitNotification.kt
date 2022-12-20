package com.depromeet.threedays.domain.entity.habit

import java.time.LocalTime

data class HabitNotification(
    val contents: String,
    val notificationTime: LocalTime
)
