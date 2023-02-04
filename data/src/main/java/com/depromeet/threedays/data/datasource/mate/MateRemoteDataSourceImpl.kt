package com.depromeet.threedays.data.datasource.mate

import com.depromeet.threedays.data.api.MateService
import com.depromeet.threedays.data.entity.base.getResult
import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.data.entity.mate.PostMateRequest
import com.depromeet.threedays.domain.exception.ThreeDaysException
import javax.inject.Inject

class MateRemoteDataSourceImpl @Inject constructor(
    private val mateService: MateService
) : MateRemoteDataSource {

    override suspend fun postMate(
        habitId: Long,
        mateEntity: PostMateRequest
    ): Result<MateEntity> {
        return mateService.postMate(habitId, mateEntity).getResult() ?: Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
    }

    override suspend fun getMateDetail(habitId: Long, mateId: Long): Result<MateEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMate(habitId: Long, mateId: Long): Result<Unit> {
        return mateService.deleteMate(habitId, mateId).getResult() ?: Result.success(Unit)
    }

    override suspend fun getMates(): Result<List<MateEntity>> {
        return mateService.getMates().getResult() ?: Result.success(emptyList())
    }

}
