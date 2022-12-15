package com.depromeet.threedays.data.datasource.notification

import com.depromeet.threedays.data.entity.notification.NotificationEntity
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor() : NotificationRemoteDataSource {
    override suspend fun setNotificationAsRead(notificationEntity: NotificationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getNotifications(): List<NotificationEntity> {
        return emptyList()
    }
}
