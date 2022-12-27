package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.auth.SignupMember
import com.depromeet.threedays.domain.entity.auth.Token
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider

interface AuthRepository {
    suspend fun createMember(certificationSubject: AuthenticationProvider, socialToken: String): SignupMember
    suspend fun saveTokensToLocal(tokens: Token)
}
