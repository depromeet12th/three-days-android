package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.HabitEntity
import retrofit2.http.*

interface HabitService {
//    @POST("/api/v1/habits")
//    suspend fun postHabit(
//        @Query("recordYmd") recordYmd: String
//    ): ApiResponse<List<Product>>

    @GET("/api/v1/habits")
    suspend fun getHabits(

    ): List<HabitEntity>

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
        @Path("habitId") habitId: Int
    ): Any
}
