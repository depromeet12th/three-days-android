package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.notification.NotificationHistoryEntity
import retrofit2.http.GET

interface NotificationHistoryService {
    @GET("/api/v1/notifications")
    suspend fun getNotificationHistories(): ApiResponse<List<NotificationHistoryEntity>>
}
