package com.depromeet.threedays.data.entity.notification

import java.time.LocalDateTime

/**
 * 알림 이력
 */
data class NotificationHistoryEntity(
    /**
     * notification history 식별자
     */
    val id: Long,
    /**
     * 회원 식별자
     */
    val memberId: Long,
    /**
     * 제목
     */
    val title: String,
    /**
     * 내용
     */
    val contents: String,
    /**
     * 알림 상태 (SUCCESS, FAILURE, CHECK)
     */
    val status: String,
    /**
     * 알림 종류
     */
    val type: String,
    /**
     * 알림 시각 (서버에서 발송한 시각)
     */
    val createAt: LocalDateTime,
)
