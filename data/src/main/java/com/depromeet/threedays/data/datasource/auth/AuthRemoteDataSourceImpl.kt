package com.depromeet.threedays.data.datasource.auth

import com.depromeet.threedays.data.api.AuthService
import com.depromeet.threedays.data.entity.auth.PostSignupRequest
import com.depromeet.threedays.data.entity.auth.SignupMemberEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import javax.inject.Inject

class AuthRemoteDataSourceImpl@Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postSignup(request: PostSignupRequest): ApiResponse<SignupMemberEntity> {
        return authService.postSignup(request = request)
    }
}
