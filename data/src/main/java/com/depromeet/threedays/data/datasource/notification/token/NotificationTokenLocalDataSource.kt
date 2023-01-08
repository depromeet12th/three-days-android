package com.depromeet.threedays.data.datasource.notification.token

interface NotificationTokenLocalDataSource {
    suspend fun getToken(): String?
}
