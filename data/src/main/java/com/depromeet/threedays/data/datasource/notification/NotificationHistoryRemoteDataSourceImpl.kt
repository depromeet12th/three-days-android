package com.depromeet.threedays.data.datasource.notification

import com.depromeet.threedays.data.api.NotificationHistoryService
import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import javax.inject.Inject

class NotificationHistoryRemoteDataSourceImpl @Inject constructor(
    private val notificationHistoryService: NotificationHistoryService,
) : NotificationHistoryRemoteDataSource {
    override suspend fun setNotificationAsRead(notificationHistoryEntity: NotificationHistoryEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getNotificationHistories(): List<NotificationHistoryEntity> {
        return notificationHistoryService.getNotificationHistories().data ?: emptyList()
    }
}
