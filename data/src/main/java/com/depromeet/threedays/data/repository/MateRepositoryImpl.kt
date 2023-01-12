package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.mate.MateRemoteDataSource
import com.depromeet.threedays.data.entity.mate.toPostMateRequest
import com.depromeet.threedays.data.mapper.toMate
import com.depromeet.threedays.domain.entity.mate.CreateMate
import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.MateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MateRepositoryImpl @Inject constructor(
    private val mateRemoteDataSource: MateRemoteDataSource
) : MateRepository {
    override suspend fun createMate(habitId: Long, mate: CreateMate): Flow<Result<Mate>> = flow {
        mateRemoteDataSource.postMate(habitId, mate.toPostMateRequest())
            .onSuccess { response ->
                emit(Result.success(value =  response.toMate()))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
    }

    override suspend fun getMateDetail(habitId: Long, mateId: Long): Flow<Result<Mate>> = flow {

    }

    override suspend fun deleteMate(habitId: Long, mateId: Long): Flow<Result<Mate?>> = flow {
        mateRemoteDataSource.deleteMate(habitId, mateId)
            .onSuccess { response ->
                emit(Result.success(value =  response?.toMate()))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
    }

    override suspend fun getMates(): Flow<Result<List<Mate>>> = flow {
        mateRemoteDataSource.getMates()
            .onSuccess { response ->
                emit(Result.success(value =  response.map { it.toMate() }))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
    }
}
