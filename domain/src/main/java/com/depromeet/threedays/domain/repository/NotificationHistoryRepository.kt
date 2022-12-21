package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import kotlinx.coroutines.flow.Flow

interface NotificationHistoryRepository {
    fun getNotificationHistories(): Flow<DataState<List<NotificationHistory>>>
}
