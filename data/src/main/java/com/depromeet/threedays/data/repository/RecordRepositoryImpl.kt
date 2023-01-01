package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.record.RecordRemoteDataSource
import com.depromeet.threedays.data.entity.record.toDomainModel
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.record.Record
import com.depromeet.threedays.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
) : RecordRepository {

    override suspend fun getRecord(to: String, from: String): Flow<DataState<Record>> = flow {
        emit(DataState.loading())
        val response = recordRemoteDataSource.getRecords(to = to, from = from)

        if (response.data != null) {
            emit(DataState.success(data = response.data.toDomainModel()))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }
}
