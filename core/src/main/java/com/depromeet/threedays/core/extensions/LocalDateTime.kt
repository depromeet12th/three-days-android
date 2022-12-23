package com.depromeet.threedays.core.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 현재 시각과 비교해서 날짜가 같으면 시분, 다르면 연월일로 표시한다
 */
fun LocalDateTime.formatRelatively(now: LocalDateTime): String {
    return format(
        if (this.toLocalDate() == now.toLocalDate()) {
            DateTimeFormatter.ofPattern("HH:mm")
        } else {
            DateTimeFormatter.ofPattern("yyyy.MM.dd")
        }
    )
}
