package com.depromeet.threedays.domain.entity

import java.time.ZonedDateTime

data class Goal(
    val goalId: Long,
    val title: String,
    val startDate: ZonedDateTime?,
    val endDate: ZonedDateTime?,
    val startTime: ZonedDateTime?,
    val notificationTime: ZonedDateTime?,
    val notificationContent: String?
)
