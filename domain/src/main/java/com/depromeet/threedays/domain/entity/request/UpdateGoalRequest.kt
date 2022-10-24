package com.depromeet.threedays.domain.entity.request

import java.time.ZonedDateTime

data class UpdateGoalRequest (
    val goalId: Long,
    val title: String,
    val startDate: ZonedDateTime? = null,
    val endDate: ZonedDateTime? = null,
    val startTime: ZonedDateTime? = null,
    val notificationTime: ZonedDateTime? = null,
    val notificationContent: String? = null
)
