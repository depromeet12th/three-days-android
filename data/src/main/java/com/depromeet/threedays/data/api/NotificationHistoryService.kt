package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import com.depromeet.threedays.data.entity.notification.NotificationHistoryReadRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationHistoryService {
    /**
     * 알림 이력 목록 조회
     */
    @GET("/api/v1/notifications")
    suspend fun getNotificationHistories(): Result<ApiResponse<List<NotificationHistoryEntity>>>

    /**
     * 알림 이력 읽음 처리
     */
    @PATCH("/api/v1/notifications/{notificationId}")
    suspend fun updateStatus(
        @Path("notificationId") notificationHistoryId: Long,
        @Body notificationHistoryReadRequest: NotificationHistoryReadRequest,
    ): Result<ApiResponse<NotificationHistoryEntity>>
}
