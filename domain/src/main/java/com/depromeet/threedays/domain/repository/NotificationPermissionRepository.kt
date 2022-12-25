package com.depromeet.threedays.domain.repository

interface NotificationPermissionRepository {
    suspend fun readNotificationPermission(key: String): String?
    suspend fun writeNotificationPermission(key: String, value: String)
}
