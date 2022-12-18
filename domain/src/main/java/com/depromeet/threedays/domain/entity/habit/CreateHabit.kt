package com.depromeet.threedays.domain.entity.habit

import com.depromeet.threedays.domain.entity.Color
import java.time.DayOfWeek
import java.time.LocalTime

data class CreateHabit(
    val color: Color,
    val dayOfWeeks: List<DayOfWeek>,
    val emoji: String,
    val notification: Notification?,
    val title: String
) {
    data class Notification(
        val contents: String,
        val notificationTime: LocalTime
    )
}
