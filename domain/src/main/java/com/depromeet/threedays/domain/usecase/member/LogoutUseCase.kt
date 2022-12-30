package com.depromeet.threedays.domain.usecase.member

import com.depromeet.threedays.domain.repository.AuthRepository
import com.depromeet.threedays.domain.repository.MemberRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        // TODO: deviceId 조회 기능 추가
        val deviceId = "identificationKey"
        memberRepository.logout(deviceId = deviceId)
        authRepository.removeTokensFromLocal()
        // TODO: notification permission 삭제
    }
}
