package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryType

fun NotificationHistoryEntity.toNotificationHistory() = NotificationHistory(
    notificationHistoryId = this.id,
    title = this.title,
    content = this.contents,
    status = NotificationHistoryStatus.from(value = this.status),
    type = NotificationHistoryType.from(value = this.type),
    createdAt = this.createAt,
)
