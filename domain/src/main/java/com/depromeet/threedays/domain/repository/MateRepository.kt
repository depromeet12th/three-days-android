package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.mate.CreateMate
import com.depromeet.threedays.domain.entity.mate.Mate
import kotlinx.coroutines.flow.Flow

interface MateRepository {
    suspend fun createMate(habitId: Long, mate: CreateMate): Flow<Result<Mate>>
    suspend fun getMateDetail(habitId: Long, mateId: Long): Flow<Result<Mate>>
    suspend fun deleteMate(habitId: Long, mateId: Long): Flow<Result<Mate?>>
    suspend fun getMates(): Flow<Result<List<Mate>>>
}
