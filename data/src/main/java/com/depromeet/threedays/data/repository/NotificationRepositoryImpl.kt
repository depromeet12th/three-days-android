package com.depromeet.threedays.data.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.notification.Notification
import com.depromeet.threedays.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(): NotificationRepository {
    override suspend fun getNotifications(): Flow<DataState<List<Notification>>> {
        // TODO: api 한테 목록 받아와서
        //  mapper 한테 넘기고
        //  리턴
        return flow {
            emit(
                DataState.success(
                    listOf(
                        1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L,
                    ).map {
                        Notification(
                            it,
                            "짝심삼일 소식",
                            "기다리고 기다리던 짝심삼일 ver.2가 출시되었습니다. 업데이트해보세요!",
                        )
                    }
                )
            )
        }
    }
}
