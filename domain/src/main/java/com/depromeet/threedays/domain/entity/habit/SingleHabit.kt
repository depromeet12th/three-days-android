package com.depromeet.threedays.domain.entity.habit

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.mate.Mate
import java.time.DayOfWeek
import java.time.LocalDateTime

data class SingleHabit (
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
) {
    companion object {
        val EMPTY = SingleHabit(
            color = Color.GREEN,
            createAt = LocalDateTime.now(),
            dayOfWeeks = emptyList(),
            id = 0,
            emoji = Emoji(""),
            mate = null,
            memberId = 0,
            notification = null,
            reward = 0,
            sequence = 0,
            status = "",
            title = "",
            todayHabitAchievementId = null,
            totalAchievementCount = 0
        )
    }
}
