package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.auth.AuthRemoteDataSource
import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
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
    private val dataStoreDataSource: DataStoreDataSource
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

    override suspend fun saveTokensToLocal(tokens: Token) {
        dataStoreDataSource.writeDataStore(ACCESS_TOKEN_KEY, tokens.accessToken)
        dataStoreDataSource.writeDataStore(REFRESH_TOKEN_KEY, tokens.refreshToken)
    }
}
