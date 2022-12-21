package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.mate.Mate
import kotlinx.coroutines.flow.Flow

interface MateRepository {
    suspend fun createMate(mateEntity: Mate): Flow<DataState<Mate>>
    suspend fun getMateDetail(habitId: Long, mateId: Long): Flow<DataState<Mate>>
    suspend fun deleteMate(habitId: Long, mateId: Long): Flow<DataState<Unit>>
    suspend fun getMates(): Flow<DataState<List<Mate>>>
}
