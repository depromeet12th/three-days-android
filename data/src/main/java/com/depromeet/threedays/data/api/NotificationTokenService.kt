package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.notification.NotificationTokenRequest
import com.depromeet.threedays.data.entity.record.RecordEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationTokenService {
    /**
     * fcm 토큰 등록
     */
    @POST("/api/v1/clients")
    suspend fun updateToken(
        @Body notificationTokenRequest: NotificationTokenRequest,
    ): ApiResponse<RecordEntity>
}
