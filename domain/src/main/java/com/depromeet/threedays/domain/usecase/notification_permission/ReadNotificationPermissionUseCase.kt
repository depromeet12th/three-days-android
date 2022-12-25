package com.depromeet.threedays.domain.usecase.notification_permission

import com.depromeet.threedays.domain.repository.NotificationPermissionRepository
import javax.inject.Inject

class ReadNotificationPermissionUseCase @Inject constructor(val repository: NotificationPermissionRepository) {
    suspend fun execute(key: String): String? = repository.readNotificationPermission(key)
}
