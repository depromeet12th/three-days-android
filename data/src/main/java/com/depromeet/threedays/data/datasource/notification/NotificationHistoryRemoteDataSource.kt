package com.depromeet.threedays.data.datasource.notification

import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus

interface NotificationHistoryRemoteDataSource {
    suspend fun getNotificationHistories(): List<NotificationHistoryEntity>
    suspend fun updateStatus(
        notificationHistoryId: Long,
        notificationHistoryStatus: NotificationHistoryStatus,
    ): NotificationHistoryEntity
}
