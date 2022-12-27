package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.record.RecordEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface RecordService {
    @GET("/api/v1/records")
    suspend fun getRecords(
        @Query("datePeriod.to") to: String,
        @Query("datePeriod.from") from: String
    ): ApiResponse<RecordEntity>
}
