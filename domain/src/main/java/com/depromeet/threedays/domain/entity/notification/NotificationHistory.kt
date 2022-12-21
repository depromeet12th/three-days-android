package com.depromeet.threedays.domain.entity.notification

import java.time.LocalDateTime

/**
 * 알림 이력
 */
data class NotificationHistory (
    /**
     * 식별자
     */
    val notificationHistoryId: Long,
    /**
     * 제목
     */
    val title: String,
    /**
     * 내용
     */
    val content: String,
    /**
     * 상태 (읽었는지 여부)
     */
    val status: NotificationHistoryStatus,
    /**
     * 알림 종류
     */
    val type: NotificationHistoryType,
    /**
     * 발송 시각 (서버 기준)
     */
    val createdAt: LocalDateTime,
)
