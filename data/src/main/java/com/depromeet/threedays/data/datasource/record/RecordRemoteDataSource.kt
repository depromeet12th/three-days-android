package com.depromeet.threedays.data.datasource.record

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.record.RecordEntity

interface RecordRemoteDataSource {
    suspend fun getRecords(to: String, from: String): ApiResponse<RecordEntity>
}
