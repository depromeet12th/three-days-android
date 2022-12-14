package com.depromeet.threedays.core.extensions

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.formatWithPattern(pattern: String): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalTime.formatHourMinute(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}
