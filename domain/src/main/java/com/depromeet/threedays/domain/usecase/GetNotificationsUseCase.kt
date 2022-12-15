package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.notification.Notification
import com.depromeet.threedays.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(): Flow<DataState<List<Notification>>> {
        return notificationRepository.getNotifications()
    }
}
