package com.depromeet.threedays.domain.usecase.notification.token

import com.depromeet.threedays.domain.repository.DeviceUniqueIdRepository
import com.depromeet.threedays.domain.repository.NotificationTokenRepository
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class UpdateNotificationTokenUseCase @Inject constructor(
    private val deviceUniqueIdRepository: DeviceUniqueIdRepository,
    private val notificationTokenRepository: NotificationTokenRepository,
) {
    suspend operator fun invoke(notificationToken: String) {
        if (notificationToken.isBlank()) {
            throw IllegalArgumentException("'notificationToken' must not be null, empty or blank. notificationToken: $notificationToken")
        }
        val deviceUniqueId = coroutineScope {
            getOrCreateDeviceUniqueId()
        }
        coroutineScope {
            notificationTokenRepository.updateToken(
                deviceUniqueId = deviceUniqueId,
                notificationToken = notificationToken,
            )
        }
    }

    private suspend fun getOrCreateDeviceUniqueId(): String {
        val deviceUniqueId = deviceUniqueIdRepository.findOne()
        if (deviceUniqueId != null) {
            return deviceUniqueId
        }
        return deviceUniqueIdRepository.save(
            deviceUniqueId = UUID.randomUUID().toString(),
        )
    }
}
