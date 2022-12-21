package com.depromeet.threedays.mypage.archived_habit

import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.mypage.archived_habit.archived_mate.ArchivedMateUI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ArchivedHabitUI(
    val habitId: Long,
    val title: String,
    val mate: ArchivedMateUI?,
    val emoji: Emoji,
    val rewardCount: Int,
    val achievementCount: Int,
    val createdAt: LocalDateTime,
    var editable: Boolean,
    var selected: Boolean,
    var mateOpened: Boolean,
) {
    companion object {
        fun from(habit: Habit) = ArchivedHabitUI(
            habitId = habit.id,
            title = habit.title,
            mate = habit.mate?.let { ArchivedMateUI.from(it) },
            emoji = Emoji.from(habit.imojiPath),
            rewardCount = habit.reward,
            achievementCount = habit.totalAchievementCount,
            createdAt = LocalDateTime.parse(habit.createAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            editable = false,
            selected = false,
            mateOpened = false,
        )
    }

    fun copyOf(
        editable: Boolean? = null,
        selected: Boolean? = null,
        mateOpened: Boolean? = null,
    ) = ArchivedHabitUI(
        habitId = this.habitId,
        title = this.title,
        mate = this.mate,
        emoji = this.emoji,
        rewardCount = this.rewardCount,
        achievementCount = this.achievementCount,
        createdAt = this.createdAt,
        editable = editable ?: this.editable,
        selected = selected ?: this.selected,
        mateOpened = mateOpened ?: this.mateOpened,
    )

    /**
     * 짝궁이 있는 습관인지
     */
    fun hasMate(): Boolean = mate != null
}
