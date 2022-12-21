package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.mate.MateEntity
import retrofit2.http.*

interface MateService {
    @POST("/api/v1/habits/{habitId}/mates")
    suspend fun postMate(
        @Query("habitId") habitId: Long,
    ): ApiResponse<MateEntity>

    @GET("/api/v1/habits/{habitId}/mates/{mateId}")
    suspend fun getMateDetail(
        @Query("habitId") habitId: Long,
        @Query("mateId") mateId: Long,
    ): ApiResponse<MateEntity>

    @DELETE("/api/v1/habits/{habitId}/mates/{mateId}")
    suspend fun deleteMate(
        @Query("habitId") habitId: Long,
        @Query("mateId") mateId: Long,
    ): ApiResponse<Unit>

    @GET("/api/v1/mates")
    suspend fun getMates(

    ): ApiResponse<List<MateEntity>>
}
