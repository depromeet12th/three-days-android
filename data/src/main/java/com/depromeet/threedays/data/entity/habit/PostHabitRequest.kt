package com.depromeet.threedays.data.entity.habit

import java.time.DayOfWeek
import java.time.LocalTime

data class PostHabitRequest(
    val color: String,
    val dayOfWeeks: List<DayOfWeek>,
    val imojiPath: String,
    val notification: Notification?,
    val title: String
) {
   data class Notification(
        val contents: String,
        val notificationTime: LocalTime
    )
}
