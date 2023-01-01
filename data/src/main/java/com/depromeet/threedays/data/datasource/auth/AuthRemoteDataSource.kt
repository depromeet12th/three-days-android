package com.depromeet.threedays.data.datasource.auth

import com.depromeet.threedays.data.entity.auth.PostSignupRequest
import com.depromeet.threedays.data.entity.auth.SignupMemberEntity

interface AuthRemoteDataSource {
    suspend fun postSignup(request: PostSignupRequest): SignupMemberEntity
}
