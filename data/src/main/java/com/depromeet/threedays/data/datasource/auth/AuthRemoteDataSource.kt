package com.depromeet.threedays.data.datasource.auth

import com.depromeet.threedays.data.entity.auth.PostSignupRequest
import com.depromeet.threedays.data.entity.auth.SignupMemberEntity
import com.depromeet.threedays.data.entity.base.ApiResponse

interface AuthRemoteDataSource {
    suspend fun postSignup(request: PostSignupRequest): Result<ApiResponse<SignupMemberEntity>>
}
