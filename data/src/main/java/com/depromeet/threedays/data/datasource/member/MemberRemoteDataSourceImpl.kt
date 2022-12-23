package com.depromeet.threedays.data.datasource.member

import com.depromeet.threedays.data.api.MemberService
import com.depromeet.threedays.data.entity.member.LogoutRequest
import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.data.entity.member.UpdateNicknameRequest
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRemoteDataSource {
    override suspend fun getMyInfo(): MemberEntity {
        return memberService.getMyInfo().data!!
    }

    override suspend fun updateNickname(nickname: String): MemberEntity {
        return memberService.updateName(
            updateNicknameRequest = UpdateNicknameRequest(
                name = nickname,
            ),
        ).data!!
    }

    override suspend fun logout(deviceId: String) {
        memberService.logout(
            logoutRequest = LogoutRequest(
                identificationKey = deviceId,
            ),
        )
    }
}
