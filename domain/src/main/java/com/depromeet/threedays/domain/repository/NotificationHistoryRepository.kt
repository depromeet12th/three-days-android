package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import kotlinx.coroutines.flow.Flow

interface NotificationHistoryRepository {
    fun getNotificationHistories(): Flow<Result<List<NotificationHistory>>>
    fun updateStatus(
        notificationHistoryId: Long,
        notificationHistoryStatus: NotificationHistoryStatus,
    ): Flow<Result<NotificationHistory>>
}
