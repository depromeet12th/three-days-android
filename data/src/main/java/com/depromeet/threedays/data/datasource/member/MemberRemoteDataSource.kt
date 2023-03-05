package com.depromeet.threedays.data.datasource.member

import com.depromeet.threedays.data.entity.member.MemberEntity

interface MemberRemoteDataSource {
    suspend fun getMyInfo(): Result<MemberEntity>
    suspend fun updateNickname(nickname: String): Result<MemberEntity>
    suspend fun logout(deviceId: String)
    suspend fun withdraw()
}
