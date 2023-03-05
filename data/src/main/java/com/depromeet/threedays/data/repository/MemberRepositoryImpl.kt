package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.member.MemberRemoteDataSource
import com.depromeet.threedays.data.mapper.toMember
import com.depromeet.threedays.domain.entity.member.Member
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
) : MemberRepository {
    override fun getMyInfo(): Flow<Result<Member>> {
        return flow {
            memberRemoteDataSource.getMyInfo()
                .onSuccess { response ->
                    emit(Result.success(value =  response.toMember()))
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    emit(Result.failure(throwable))
                }
        }
    }

    override fun updateNickname(nickname: String): Flow<Result<Member>> {
        return flow {
            memberRemoteDataSource.updateNickname(nickname)
                .onSuccess { response ->
                    emit(Result.success(value =  response.toMember()))
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    emit(Result.failure(throwable))
                }
        }
    }

    override suspend fun logout(deviceId: String) {
        memberRemoteDataSource.logout(deviceId = deviceId)
    }

    override suspend fun withdraw() {
        memberRemoteDataSource.withdraw()
    }
}
