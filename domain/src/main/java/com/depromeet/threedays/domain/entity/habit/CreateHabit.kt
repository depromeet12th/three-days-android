package com.depromeet.threedays.domain.entity.habit

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import java.time.DayOfWeek
import java.time.LocalTime

data class CreateHabit(
    val color: Color,
    val dayOfWeeks: List<DayOfWeek>,
    val emoji: Emoji,
    val notification: Notification?,
    val title: String
) {
    data class Notification(
        val contents: String,
        val notificationTime: LocalTime
    )
}
