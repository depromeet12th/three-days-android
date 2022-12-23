package com.depromeet.threedays.data.entity.member

/**
 * 회원 정보
 */
data class MemberEntity(
    /**
     * 아이디
     */
    val id: Long,
    /**
     * 닉네임
     */
    val name: String,
    /**
     * 인증 제공자 (GOOGLE, KAKAO)
     */
    val certificationSubject: String,
    /**
     * 알림 수신 여부
     */
    val notificationConsent: Boolean,
    /**
     * key-value store
     */
    val resource: Map<String, Any>
)
