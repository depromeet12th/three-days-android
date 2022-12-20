package com.depromeet.threedays.data.entity.habit

import com.depromeet.threedays.domain.entity.habit.HabitNotification
import com.depromeet.threedays.domain.entity.mate.Mate
import java.time.DayOfWeek
import java.time.LocalDateTime

data class SingleHabitEntity(
    val color: String,
    val createAt: LocalDateTime,
    val dayOfWeeks: List<DayOfWeek>,
    val id: Long,
    val imojiPath: String,
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
