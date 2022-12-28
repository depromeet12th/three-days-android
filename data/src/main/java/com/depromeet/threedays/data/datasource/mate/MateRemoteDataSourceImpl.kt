package com.depromeet.threedays.data.datasource.mate

import com.depromeet.threedays.data.api.MateService
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.data.entity.mate.PostMateRequest
import javax.inject.Inject

class MateRemoteDataSourceImpl @Inject constructor(
    private val mateService: MateService
) : MateRemoteDataSource {

    override suspend fun postMate(
        habitId: Long,
        mateEntity: PostMateRequest
    ): ApiResponse<MateEntity> {
        return mateService.postMate(habitId, mateEntity)
    }

    override suspend fun getMateDetail(habitId: Long, mateId: Long): ApiResponse<MateEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMate(habitId: Long, mateId: Long): ApiResponse<MateEntity?> {
        return mateService.deleteMate(habitId, mateId)
    }

    override suspend fun getMates(): ApiResponse<List<MateEntity>> {
        return mateService.getMates()
    }

}
