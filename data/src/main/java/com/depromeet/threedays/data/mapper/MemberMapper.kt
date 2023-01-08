package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.auth.SignupMemberEntity
import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.domain.entity.auth.SignupMember
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.entity.member.Member
import com.depromeet.threedays.domain.key.RESOURCE_CREATE

fun MemberEntity.toMember() = Member(
    memberId = this.id,
    name = this.name,
    authenticationProvider = AuthenticationProvider.from(this.certificationSubject),
    notificationEnabled = this.notificationConsent,
    resource = this.resource,
)

fun ApiResponse<SignupMemberEntity>.toSignupMember(): SignupMember {
    val data = this.data ?: throw IllegalStateException()
    val code = this.code
    return SignupMember(
        id = data.id,
        name = data.name,
        certificationSubject = AuthenticationProvider.from(data.certificationSubject),
        token = data.token,
        resource = data.resource,
        notificationConsent = data.notificationConsent,
        isSignedUp = (code != RESOURCE_CREATE)
    )
}
