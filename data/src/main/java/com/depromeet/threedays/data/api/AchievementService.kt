package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.achievement.AchievementDateEntity
import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.habit.HabitEntity
import retrofit2.http.*
import java.time.LocalDate

interface AchievementService {
    @GET("/api/v1/habits/{habitId}/achievements")
    suspend fun getHabitAchievements(
        @Path("habitId") habitId: Long,
        @Query("datePeriod.to") to: LocalDate,
        @Query("datePeriod.from") from: LocalDate
    ): Result<ApiResponse<List<AchievementEntity>>>

    @POST("/api/v1/habits/{habitId}/achievements")
    suspend fun postHabitAchievement(
        @Path("habitId") habitId: Long,
        @Body request: AchievementDateEntity,
    ): Result<ApiResponse<HabitEntity>>

    @DELETE("/api/v1/habits/{habitId}/achievements/{habitAchievementId}")
    suspend fun deleteHabitAchievement(
        @Path("habitId") habitId: Long,
        @Path("habitAchievementId") habitAchievementId: Long,
    ): Result<ApiResponse<HabitEntity>>
}
