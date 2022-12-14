package com.depromeet.threedays.domain.usecase.member

import com.depromeet.threedays.domain.repository.AuthRepository
import com.depromeet.threedays.domain.repository.DeviceUniqueIdRepository
import com.depromeet.threedays.domain.repository.LocalDataRepository
import com.depromeet.threedays.domain.repository.MemberRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val deviceUniqueIdRepository: DeviceUniqueIdRepository,
    private val authRepository: AuthRepository,
    private val localDataRepository: LocalDataRepository,
) {
    suspend operator fun invoke() {
        deviceUniqueIdRepository.findOne()?.let { deviceId ->
            memberRepository.logout(deviceId = deviceId)
        }
        authRepository.removeTokensFromLocal()
        localDataRepository.removeAll()
    }
}
