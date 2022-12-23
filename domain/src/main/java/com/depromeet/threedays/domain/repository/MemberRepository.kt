package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.member.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun getMyInfo(): Flow<DataState<Member>>
    fun updateNickname(nickname: String): Flow<DataState<Member>>
    fun logout(deviceId: String): Flow<DataState<Unit>>
    fun withdraw(): Flow<DataState<Unit>>
}
