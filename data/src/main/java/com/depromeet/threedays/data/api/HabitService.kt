package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.request.PostHabitRequest
import retrofit2.http.*

interface HabitService {
    @POST("/api/v1/habits")
    suspend fun postHabit(
        @Body request: PostHabitRequest
    )

    @GET("/api/v1/habits")
    suspend fun getHabits(

    ): ApiResponse<List<HabitEntity>>

//    @GET("/api/v1/habits/{habitId}")
//    suspend fun getHabit(
//        @Body request: PostProductsRequest
//    )

    @PUT("/api/v1/habits/{habitId}")
    suspend fun updateHabit(
        @Path("habitId") habitId: Long,
        @Body request: PostHabitRequest
    )

    @DELETE("/api/v1/habits/{habitId}")
    suspend fun deleteHabit(
        @Path("productId") productId: Long
    )
}
