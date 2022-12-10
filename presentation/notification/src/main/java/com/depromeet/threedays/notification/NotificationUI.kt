package com.depromeet.threedays.notification

import com.depromeet.threedays.domain.entity.notification.Notification

data class NotificationUI(
    val id: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun from(notification: Notification) = NotificationUI(
            id = notification.notificationId,
            title = notification.title,
            content = notification.content
        )
    }
}
