package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.notification.NotificationEntity
import com.depromeet.threedays.domain.entity.notification.Notification

fun NotificationEntity.toNotification() = Notification(
    this.notificationId,
    this.title,
    this.content,
)
