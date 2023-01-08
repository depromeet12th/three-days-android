package com.depromeet.threedays.domain.repository

interface NotificationTokenRepository {
    suspend fun updateToken(
        deviceUniqueId: String,
        notificationToken: String,
    )

    suspend fun getToken(): String?
}
