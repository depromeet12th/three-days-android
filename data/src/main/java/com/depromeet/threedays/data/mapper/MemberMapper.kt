package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.member.MemberEntity
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.entity.member.Member

fun MemberEntity.toMember() = Member(
    memberId = this.id,
    name = this.name,
    authenticationProvider = AuthenticationProvider.from(this.certificationSubject),
    notificationEnabled = this.notificationConsent,
    resource = this.resource,
)
