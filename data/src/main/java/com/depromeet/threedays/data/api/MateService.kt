package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.data.entity.mate.PostMateRequest
import retrofit2.http.*

interface MateService {
    // TODO: postMate, getMates를 제외하고 나머지는 제대로 다시 만들어야함
    @POST("/api/v1/habits/{habitId}/mates")
    suspend fun postMate(
        @Path("habitId") habitId: Long,
        @Body request: PostMateRequest,
    ): ApiResponse<MateEntity>

    @GET("/api/v1/habits/{habitId}/mates/{mateId}")
    suspend fun getMateDetail(
        @Query("habitId") habitId: Long,
        @Query("mateId") mateId: Long,
    ): ApiResponse<MateEntity>

    @DELETE("/api/v1/habits/{habitId}/mates/{mateId}")
    suspend fun deleteMate(
        @Path("habitId") habitId: Long,
        @Path("mateId") mateId: Long,
    ): ApiResponse<MateEntity?>

    @GET("/api/v1/mates")
    suspend fun getMates(

    ): ApiResponse<List<MateEntity>>
}
