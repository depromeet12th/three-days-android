package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.notification.NotificationHistoryRemoteDataSource
import com.depromeet.threedays.data.mapper.toNotificationHistory
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationHistoryRepositoryImpl @Inject constructor(
    private val notificationHistoryRemoteDataSource: NotificationHistoryRemoteDataSource,
) : NotificationHistoryRepository {
    override fun getNotificationHistories(): Flow<DataState<List<NotificationHistory>>> {
        return flow {
            emit(DataState.loading())
            notificationHistoryRemoteDataSource.getNotificationHistories().apply {
                emit(
                    DataState.success(
                        data = this.map { it.toNotificationHistory() }
                    )
                )
            }.runCatching {
                emit(DataState.fail("response is fail"))
            }
        }
    }
}
