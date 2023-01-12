package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.member.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun getMyInfo(): Flow<Result<Member>>
    fun updateNickname(nickname: String): Flow<Result<Member>>
    suspend fun logout(deviceId: String)
    suspend fun withdraw()
}
