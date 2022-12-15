package com.depromeet.threedays.data.entity.notification

/**
 * 서버 응답에 맞추기
 */
data class NotificationEntity(
    val notificationId: Long,
    val title: String,
    val content: String,
    val isRead: Boolean,
)
