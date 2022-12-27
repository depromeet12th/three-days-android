package com.depromeet.threedays.data.entity.auth

import com.depromeet.threedays.domain.entity.auth.Token

class SignupMemberEntity (
    val certificationSubject: String,
    val id: Long,
    val name: String,
    val notificationConsent: Boolean,
    val resource: String,
    val token: Token
)
