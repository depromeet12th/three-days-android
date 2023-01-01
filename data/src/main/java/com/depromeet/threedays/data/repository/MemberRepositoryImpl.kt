package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.member.MemberRemoteDataSource
import com.depromeet.threedays.data.mapper.toMember
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.member.Member
import com.depromeet.threedays.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
) : MemberRepository {
    override fun getMyInfo(): Flow<DataState<Member>> {
        return flow {
            emit(DataState.loading())
            memberRemoteDataSource.getMyInfo().apply {
                emit(
                    DataState.success(
                        data = this.toMember()
                    )
                )
            }.runCatching {
                emit(DataState.fail("Failed to get response"))
            }
        }
    }

    override fun updateNickname(nickname: String): Flow<DataState<Member>> {
        return flow {
            emit(DataState.loading())
            memberRemoteDataSource.updateNickname(nickname).apply {
                emit(
                    DataState.success(
                        data = this.toMember()
                    )
                )
            }.runCatching {
                emit(DataState.fail("Failed to get response"))
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
