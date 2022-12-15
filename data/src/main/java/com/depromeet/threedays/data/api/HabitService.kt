package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import retrofit2.http.GET

interface HabitService {
//    @POST("/api/v1/habits")
//    suspend fun postHabit(
//        @Query("recordYmd") recordYmd: String
//    ): ApiResponse<List<Product>>

    @GET("/api/v1/habits")
    suspend fun getHabits(

    ): ApiResponse<List<HabitEntity>>

    @GET("/api/v1/habits?status=ARCHIVED")
    suspend fun getArchivedHabits(): List<HabitEntity>

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
//    @DELETE("/api/v1/habits/{habitId}")
//    suspend fun deleteHabit(
//        @Path("productId") productId: String
//    )
}
