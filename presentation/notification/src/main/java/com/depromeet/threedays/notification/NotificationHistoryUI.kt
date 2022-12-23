package com.depromeet.threedays.notification

import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import java.time.LocalDateTime

data class NotificationHistoryUI(
    val id: Long,
    val title: String,
    val content: String,
    var status: NotificationHistoryStatus,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(notificationHistory: NotificationHistory) = NotificationHistoryUI(
            id = notificationHistory.notificationHistoryId,
            title = notificationHistory.title,
            content = notificationHistory.content,
            status = notificationHistory.status,
            createdAt = notificationHistory.createdAt,
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
            createdAt = this.createdAt,
        )
    }
}
