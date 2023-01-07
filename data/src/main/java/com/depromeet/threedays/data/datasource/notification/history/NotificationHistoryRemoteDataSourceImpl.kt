package com.depromeet.threedays.data.datasource.notification.history

import com.depromeet.threedays.data.api.NotificationHistoryService
import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import com.depromeet.threedays.data.entity.notification.NotificationHistoryReadRequest
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import javax.inject.Inject

class NotificationHistoryRemoteDataSourceImpl @Inject constructor(
    private val notificationHistoryService: NotificationHistoryService,
) : NotificationHistoryRemoteDataSource {
    override suspend fun getNotificationHistories(): List<NotificationHistoryEntity> {
        return notificationHistoryService.getNotificationHistories().data ?: emptyList()
    }

    override suspend fun updateStatus(
        notificationHistoryId: Long,
        notificationHistoryStatus: NotificationHistoryStatus,
    ): NotificationHistoryEntity {
        return notificationHistoryService.updateStatus(
            notificationHistoryId = notificationHistoryId,
            notificationHistoryReadRequest = NotificationHistoryReadRequest(
                status = notificationHistoryStatus,
            ),
        ).data!!
    }
}
