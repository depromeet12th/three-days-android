package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.data.entity.member.UpdateNicknameRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MemberService {
    @GET("/api/v1/members/me")
    suspend fun getMyInfo(): ApiResponse<MemberEntity>

    @PATCH("/api/v1/members/name")
    suspend fun updateName(
        @Body updateNicknameRequest: UpdateNicknameRequest
    ): ApiResponse<MemberEntity>
}
