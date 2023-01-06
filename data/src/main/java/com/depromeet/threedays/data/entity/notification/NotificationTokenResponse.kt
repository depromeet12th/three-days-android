package com.depromeet.threedays.data.entity.notification

data class NotificationTokenResponse(
    val id: Long,
    val memberId: Long,
    val fcmToken: String,
    val identificationKey: String,
)
