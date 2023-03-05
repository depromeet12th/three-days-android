package com.depromeet.threedays.data.datasource.notification.history

import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus

interface NotificationHistoryRemoteDataSource {
    suspend fun getNotificationHistories(): Result<List<NotificationHistoryEntity>>
    suspend fun updateStatus(
        notificationHistoryId: Long,
        notificationHistoryStatus: NotificationHistoryStatus,
    ): Result<NotificationHistoryEntity>
}
