package com.depromeet.threedays.domain.usecase.notification

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationHistoriesUseCase @Inject constructor(
    private val notificationHistoryRepository: NotificationHistoryRepository,
) {
    operator fun invoke(): Flow<DataState<List<NotificationHistory>>> {
        return notificationHistoryRepository.getNotificationHistories()
    }
}
