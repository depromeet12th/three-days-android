package com.depromeet.threedays.data.datasource.member

import com.depromeet.threedays.data.api.MemberService
import com.depromeet.threedays.data.entity.base.getResult
import com.depromeet.threedays.data.entity.member.LogoutRequest
import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.data.entity.member.UpdateNicknameRequest
import com.depromeet.threedays.domain.exception.ThreeDaysException
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRemoteDataSource {
    override suspend fun getMyInfo(): Result<MemberEntity> {
        return memberService.getMyInfo().getResult() ?: Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
    }

    override suspend fun updateNickname(nickname: String): Result<MemberEntity> {
        return memberService.updateName(
            updateNicknameRequest = UpdateNicknameRequest(
                name = nickname,
            ),
        ).getResult() ?: Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
    }

    override suspend fun logout(deviceId: String) {
        memberService.logout(
            logoutRequest = LogoutRequest(
                identificationKey = deviceId,
            ),
        )
    }

    override suspend fun withdraw() {
        memberService.withdraw()
    }
}
