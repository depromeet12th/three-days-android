package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.mate.MateRemoteDataSource
import com.depromeet.threedays.data.entity.mate.toPostMateRequest
import com.depromeet.threedays.data.mapper.toMate
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.mate.CreateMate
import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.repository.MateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MateRepositoryImpl @Inject constructor(
    private val mateRemoteDataSource: MateRemoteDataSource
) : MateRepository {
    override suspend fun createMate(habitId: Long, mate: CreateMate): Flow<DataState<Mate>> = flow {
        emit(DataState.loading())

        val response = mateRemoteDataSource.postMate(habitId, mate.toPostMateRequest())

        if (response.data != null) {
            emit(DataState.success(data = response.data.toMate()))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }

    override suspend fun getMateDetail(habitId: Long, mateId: Long): Flow<DataState<Mate>> = flow {

    }

    override suspend fun deleteMate(habitId: Long, mateId: Long): Flow<DataState<Mate?>> = flow {
        emit(DataState.loading())

        val response = mateRemoteDataSource.deleteMate(habitId, mateId)

        if (response.data != null) {
            emit(DataState.success(data = response.data.toMate()))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }

    override suspend fun getMates(): Flow<DataState<List<Mate>>> = flow {
        emit(DataState.loading())
        val response = mateRemoteDataSource.getMates()

        if (response.data != null) {
            emit(DataState.success(data = response.data.map { it.toMate() }))
        } else {
            emit(DataState.error(msg = "response has error"))
        }.runCatching {
            emit(DataState.fail("response is fail"))
        }
    }

}
