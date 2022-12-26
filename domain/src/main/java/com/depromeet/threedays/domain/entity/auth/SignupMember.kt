package com.depromeet.threedays.domain.entity.auth

data class SignupMember(
    val certificationSubject: Certification,
    val id: Long,
    val name: String,
    val notificationConsent: Boolean,
    val resource: Resource,
    val token: Token
) {
    enum class Certification { KAKAO, GOOGLE }
}
