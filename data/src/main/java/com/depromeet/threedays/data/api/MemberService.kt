package com.depromeet.threedays.data.api

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.member.LogoutRequest
import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.data.entity.member.UpdateNicknameRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface MemberService {
    @GET("/api/v1/members/me")
    suspend fun getMyInfo(): ApiResponse<MemberEntity>

    @PATCH("/api/v1/members/name")
    suspend fun updateName(
        @Body updateNicknameRequest: UpdateNicknameRequest,
    ): ApiResponse<MemberEntity>

    @POST("/api/v1/members/logout")
    suspend fun logout(
        @Body logoutRequest: LogoutRequest,
    ): ApiResponse<Unit>
}
