package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface AchievementService {
    @GET("/api/v1/habits/{habitId}/achievements")
    suspend fun getHabitAchievements(
        @Path("habitId") habitId: Long,
        @Query("datePeriod.to") to: LocalDate,
        @Query("datePeriod.from") from: LocalDate
    ): ApiResponse<List<AchievementEntity>>
}
