package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.notification.token.NotificationTokenLocalDataSource
import com.depromeet.threedays.data.datasource.notification.token.NotificationTokenRemoteDataSource
import com.depromeet.threedays.data.entity.notification.NotificationTokenRequest
import com.depromeet.threedays.domain.repository.NotificationTokenRepository
import javax.inject.Inject

class NotificationTokenRepositoryImpl @Inject constructor(
    private val notificationTokenRemoteDataSource: NotificationTokenRemoteDataSource,
    private val notificationTokenLocalDataSource: NotificationTokenLocalDataSource,
): NotificationTokenRepository {
    override suspend fun updateToken(
        deviceUniqueId: String,
        notificationToken: String
    ) {
        notificationTokenRemoteDataSource.updateToken(
            notificationTokenRequest = NotificationTokenRequest(
                fcmToken = notificationToken,
                identificationKey = deviceUniqueId,
            )
        )
    }

    override suspend fun getToken(): String? {
        return notificationTokenLocalDataSource.getToken()
    }
}
