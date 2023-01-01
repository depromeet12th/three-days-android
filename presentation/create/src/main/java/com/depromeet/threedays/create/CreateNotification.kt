package com.depromeet.threedays.create

import java.time.LocalTime

data class CreateNotification (
    val initNotificationTime: Boolean,
    val notificationInfoActive: Boolean,
    val notificationContent: String,
    val notificationTime: LocalTime
)
