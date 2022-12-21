package com.depromeet.threedays.data.datasource.member

import com.depromeet.threedays.data.entity.member.MemberEntity

interface MemberRemoteDataSource {
    suspend fun getMyInfo(): MemberEntity
    suspend fun updateNickname(nickname: String): MemberEntity
}
