package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.notification.history.NotificationHistoryRemoteDataSource
import com.depromeet.threedays.data.mapper.toNotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistory
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationHistoryRepositoryImpl @Inject constructor(
    private val notificationHistoryRemoteDataSource: NotificationHistoryRemoteDataSource,
) : NotificationHistoryRepository {
    override fun getNotificationHistories(): Flow<Result<List<NotificationHistory>>> {
        return flow {
            notificationHistoryRemoteDataSource.getNotificationHistories()
                .onSuccess { response ->
                    emit(Result.success(value =  response.map{ it.toNotificationHistory() }))
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    emit(Result.failure(throwable))
                }
        }
    }

    override fun updateStatus(
        notificationHistoryId: Long,
        notificationHistoryStatus: NotificationHistoryStatus,
    ): Flow<Result<NotificationHistory>> {
        return flow {
            notificationHistoryRemoteDataSource.updateStatus(
                notificationHistoryId = notificationHistoryId,
                notificationHistoryStatus = NotificationHistoryStatus.CHECKED,
            ).onSuccess { response ->
                emit(Result.success(value =  response.toNotificationHistory()))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
        }
    }
}
