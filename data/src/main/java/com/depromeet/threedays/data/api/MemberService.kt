package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.member.MemberEntity
import retrofit2.http.GET

interface MemberService {
    @GET("/api/v1/members/me")
    suspend fun getMyInfo(): ApiResponse<MemberEntity>
}
