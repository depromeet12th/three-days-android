package com.depromeet.threedays.domain.usecase.notification_permission

import com.depromeet.threedays.domain.repository.NotificationPermissionRepository
import javax.inject.Inject

class WriteNotificationPermissionUseCase @Inject constructor(val repository: NotificationPermissionRepository) {
    suspend fun execute(key: String, value: String) = repository.writeNotificationPermission(key = key, value = value)
}
