package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.ThreeDaysSharedPreference
import com.depromeet.threedays.data.datasource.auth.AuthRemoteDataSource
import com.depromeet.threedays.data.entity.auth.PostSignupRequest
import com.depromeet.threedays.data.mapper.toSignupMember
import com.depromeet.threedays.domain.entity.auth.SignupMember
import com.depromeet.threedays.domain.entity.auth.Token
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.key.ACCESS_TOKEN_KEY
import com.depromeet.threedays.domain.key.REFRESH_TOKEN_KEY
import com.depromeet.threedays.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val threeDaysSharedPreference: ThreeDaysSharedPreference
): AuthRepository {
    override suspend fun createMember(
        certificationSubject: AuthenticationProvider,
        socialToken: String
    ): SignupMember {
        val request = PostSignupRequest(
            certificationSubject = AuthenticationProvider.from(certificationSubject),
            socialToken = socialToken
        )
        return authRemoteDataSource.postSignup(request = request).toSignupMember()
    }

    override fun saveTokensToLocal(tokens: Token) {
        threeDaysSharedPreference.putString(ACCESS_TOKEN_KEY, tokens.accessToken)
        threeDaysSharedPreference.putString(REFRESH_TOKEN_KEY, tokens.refreshToken)
    }

    override fun getAccessTokenFromLocal(): String {
        return threeDaysSharedPreference.getString(ACCESS_TOKEN_KEY)
    }

    override fun removeTokensFromLocal() {
        threeDaysSharedPreference.remove(ACCESS_TOKEN_KEY)
        threeDaysSharedPreference.remove(REFRESH_TOKEN_KEY)
    }
}
