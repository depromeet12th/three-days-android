package com.depromeet.threedays.data.datasource.mate

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.mate.MateEntity

interface MateRemoteDataSource {
    suspend fun postMate(mateEntity: MateEntity): ApiResponse<MateEntity>
    suspend fun getMate(habitId: Long, mateId: Long): ApiResponse<MateEntity>
    suspend fun deleteMate(habitId: Long, mateId: Long): ApiResponse<Unit>
}
