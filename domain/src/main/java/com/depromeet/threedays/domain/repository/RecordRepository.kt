package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.record.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun getRecord(to: String, from: String): Flow<Result<Record>>
}
