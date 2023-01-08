package com.depromeet.threedays.data.entity.notification

data class NotificationTokenRequest(
    val fcmToken: String,
    val identificationKey: String,
)
