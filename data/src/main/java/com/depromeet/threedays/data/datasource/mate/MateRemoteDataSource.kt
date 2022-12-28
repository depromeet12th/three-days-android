package com.depromeet.threedays.data.datasource.mate

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.data.entity.mate.PostMateRequest

interface MateRemoteDataSource {
    suspend fun postMate(habitId: Long, mateEntity: PostMateRequest): ApiResponse<MateEntity>
    suspend fun getMateDetail(habitId: Long, mateId: Long): ApiResponse<MateEntity>
    suspend fun deleteMate(habitId: Long, mateId: Long): ApiResponse<Unit>
    suspend fun getMates(): ApiResponse<List<MateEntity>>
}
