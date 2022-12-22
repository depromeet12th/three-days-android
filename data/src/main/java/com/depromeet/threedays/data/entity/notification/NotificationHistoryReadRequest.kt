package com.depromeet.threedays.data.entity.notification

import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus

data class NotificationHistoryReadRequest(
    val status: NotificationHistoryStatus,
)
