package com.depromeet.threedays.data.datasource.notification.token

import com.depromeet.threedays.data.entity.notification.NotificationTokenRequest

interface NotificationTokenRemoteDataSource {
    suspend fun updateToken(
        notificationTokenRequest: NotificationTokenRequest,
    )
}
