package com.depromeet.threedays.domain.entity.member

data class Member(
    /**
     * 회원 식별자
     */
    val memberId: Long,
    /**
     * 닉네임
     */
    var name: String,
    /**
     * 인증 제공자
     */
    val authenticationProvider: AuthenticationProvider,
    /**
     * 알림 수신 여부
     */
    var notificationEnabled: Boolean,
    /**
     * 기타 설정 저장
     */
    val resource: Map<String, Any>,
)
