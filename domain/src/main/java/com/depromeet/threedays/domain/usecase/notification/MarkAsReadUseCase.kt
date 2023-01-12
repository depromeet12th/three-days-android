package com.depromeet.threedays.domain.usecase.notification

import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import com.depromeet.threedays.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 알림 이력 1개 선택해서 읽음처리
 */
class MarkAsReadUseCase @Inject constructor(
    private val notificationHistoryRepository: NotificationHistoryRepository,
) {
    operator fun invoke(notificationHistoryId: Long): Flow<Result<NotificationHistory>> {
        return notificationHistoryRepository.updateStatus(
            notificationHistoryId = notificationHistoryId,
            notificationHistoryStatus = NotificationHistoryStatus.CHECKED,
        )
    }
}
