package com.depromeet.threedays.domain.usecase.member

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    operator fun invoke(): Flow<DataState<Unit>> {
        // TODO: deviceId 조회 기능 추가
        val deviceId = "identificationKey"
        return memberRepository.logout(deviceId = deviceId)
        // TODO: 성공하면 accessToken, refreshToken 삭제
    }
}
