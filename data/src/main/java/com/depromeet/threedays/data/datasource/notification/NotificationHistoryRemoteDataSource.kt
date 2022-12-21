package com.depromeet.threedays.data.datasource.notification

import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity

interface NotificationHistoryRemoteDataSource {
    suspend fun setNotificationAsRead(notificationHistoryEntity: NotificationHistoryEntity)
    suspend fun getNotificationHistories(): List<NotificationHistoryEntity>
}
