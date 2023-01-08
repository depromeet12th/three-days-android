package com.depromeet.threedays.domain.entity.auth

import com.depromeet.threedays.domain.entity.member.AuthenticationProvider

data class SignupMember(
    val certificationSubject: AuthenticationProvider,
    val id: Long,
    val name: String,
    val notificationConsent: Boolean,
    val resource: String,
    val token: Token,
    val isSignedUp: Boolean
)
