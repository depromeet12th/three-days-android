package com.depromeet.threedays.data.datasource.notification.token

import com.depromeet.threedays.data.api.NotificationTokenService
import com.depromeet.threedays.data.entity.notification.NotificationTokenRequest
import timber.log.Timber
import javax.inject.Inject

class NotificationTokenRemoteDataSourceImpl @Inject constructor(
    private val notificationTokenService: NotificationTokenService,
) : NotificationTokenRemoteDataSource {
    override suspend fun updateToken(
        notificationTokenRequest: NotificationTokenRequest,
    ) {
        try {
            notificationTokenService.updateToken(
                notificationTokenRequest = notificationTokenRequest,
            )
        } catch (e: Exception) {
            Timber.e(
                e,
                "Failed to register fcmToken. notificationTokenRequest: $notificationTokenRequest"
            )
        }
    }
}
