package com.depromeet.threedays.data.datasource.mate

import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.data.entity.mate.PostMateRequest

interface MateRemoteDataSource {
    suspend fun postMate(habitId: Long, mateEntity: PostMateRequest): Result<MateEntity>
    suspend fun getMateDetail(habitId: Long, mateId: Long): Result<MateEntity>
    suspend fun deleteMate(habitId: Long, mateId: Long): Result<Unit>
    suspend fun getMates(): Result<List<MateEntity>>
}
