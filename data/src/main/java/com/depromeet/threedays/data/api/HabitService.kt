package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.request.PostHabitRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path

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
//
//    @PATCH("/api/v1/habits/{habitId}")
//    suspend fun updateHabit(
//        @Body request: UpdateProductsRequest
//    )
//
    @DELETE("/api/v1/habits/{habitId}")
    suspend fun deleteHabit(
        @Path("productId") productId: Int
    )
}
