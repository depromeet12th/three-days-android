package com.depromeet.threedays.notification

import com.depromeet.threedays.domain.entity.notification.NotificationHistory

data class NotificationHistoryUI(
    val id: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun from(notification: NotificationHistory) = NotificationHistoryUI(
            id = notification.notificationHistoryId,
            title = notification.title,
            content = notification.content
        )
    }
}
