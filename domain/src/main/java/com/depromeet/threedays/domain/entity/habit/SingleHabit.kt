package com.depromeet.threedays.domain.entity.habit

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.mate.Mate
import java.time.DayOfWeek
import java.time.LocalDateTime

class SingleHabit (
    val color: Color,
    val createAt: LocalDateTime,
    val dayOfWeeks: List<DayOfWeek>,
    val id: Long,
    val emoji: Emoji,
    val mate: Mate?,
    val memberId: Long,
    val notification: HabitNotification?,
    val reward: Long,
    val sequence: Int,
    val status: String,
    val title: String,
    val todayHabitAchievementId: Long?,
    val totalAchievementCount: Long
)
