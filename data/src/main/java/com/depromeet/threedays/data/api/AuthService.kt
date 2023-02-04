package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.auth.PostSignupRequest
import com.depromeet.threedays.data.entity.auth.SignupMemberEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/members")
    suspend fun postSignup(
        @Body request: PostSignupRequest,
    ): Result<ApiResponse<SignupMemberEntity>>
}
