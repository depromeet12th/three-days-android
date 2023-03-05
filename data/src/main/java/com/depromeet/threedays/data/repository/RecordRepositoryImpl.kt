package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.record.RecordRemoteDataSource
import com.depromeet.threedays.data.entity.record.toDomainModel
import com.depromeet.threedays.domain.entity.record.Record
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
) : RecordRepository {

    override suspend fun getRecord(to: String, from: String): Flow<Result<Record>> = flow {
        recordRemoteDataSource.getRecords(to = to, from = from)
            .onSuccess { response ->
                emit(Result.success(value =  response.toDomainModel()))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
    }
}
