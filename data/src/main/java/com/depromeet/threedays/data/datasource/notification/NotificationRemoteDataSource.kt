package com.depromeet.threedays.data.datasource.notification

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.notification.NotificationEntity

interface NotificationRemoteDataSource {
    suspend fun setNotificationAsRead(notificationEntity: NotificationEntity)
    suspend fun getNotifications(): List<NotificationEntity>
}
