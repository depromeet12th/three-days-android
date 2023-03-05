package com.depromeet.threedays.domain.usecase.auth

import com.depromeet.threedays.domain.entity.auth.SignupMember
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.AuthRepository
import javax.inject.Inject

class CreateMemberUserCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        certificationSubject: AuthenticationProvider,
        socialToken: String
    ): Result<SignupMember> {
        return authRepository.createMember(
            certificationSubject = certificationSubject,
            socialToken = socialToken
        ).onSuccess {
            authRepository.saveTokensToLocal(it.token)
        }.onFailure {
            throw it as ThreeDaysException
        }
    }
}
