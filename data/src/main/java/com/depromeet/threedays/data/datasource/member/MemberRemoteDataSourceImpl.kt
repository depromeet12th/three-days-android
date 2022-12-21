package com.depromeet.threedays.data.datasource.member

import com.depromeet.threedays.data.api.MemberService
import com.depromeet.threedays.data.entity.member.MemberEntity
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRemoteDataSource {
    override suspend fun getMyInfo(): MemberEntity {
        return memberService.getMyInfo().data!!
    }
}
