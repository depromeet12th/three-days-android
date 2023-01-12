package com.depromeet.threedays.data.datasource.record

import com.depromeet.threedays.data.api.RecordService
import com.depromeet.threedays.data.entity.base.getResult
import com.depromeet.threedays.data.entity.record.RecordEntity
import javax.inject.Inject

class RecordRemoteDataSourceImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRemoteDataSource {

    override suspend fun getRecords(to: String, from: String): Result<RecordEntity> {
        return recordService.getRecords(to = to, from = from).getResult()
    }
}
