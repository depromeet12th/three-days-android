package com.depromeet.threedays.mypage.archived_habit

import com.depromeet.threedays.domain.entity.habit.Habit

data class ArchivedHabitUI(
    val habitId: Long,
    val title: String,
) {
    companion object {
        fun from(habit: Habit) = ArchivedHabitUI(
            habitId = habit.habitId.toLong(),
            title = habit.title,
        )
    }
}
