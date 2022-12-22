package com.depromeet.threedays.notification

import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus

data class NotificationHistoryUI(
    val id: Long,
    val title: String,
    val content: String,
    var status: NotificationHistoryStatus,
) {
    companion object {
        fun from(notificationHistory: NotificationHistory) = NotificationHistoryUI(
            id = notificationHistory.notificationHistoryId,
            title = notificationHistory.title,
            content = notificationHistory.content,
            status = notificationHistory.status,
        )
    }

    fun copyOf(
        status: NotificationHistoryStatus?,
    ): NotificationHistoryUI {
        return NotificationHistoryUI(
            id = this.id,
            title = this.title,
            content = this.content,
            status = status ?: this.status,
        )
    }
}
