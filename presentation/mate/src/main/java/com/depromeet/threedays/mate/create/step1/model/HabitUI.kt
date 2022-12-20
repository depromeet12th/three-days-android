package com.depromeet.threedays.mate.create.step1.model

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.Habit

data class HabitUI(
    val habitId: Long,
    val title: String,
    val imojiPath: String,
    val color: Color,
    val clicked: Boolean = false
)

fun Habit.toHabitUI(): HabitUI {
    return HabitUI(
        habitId = habitId,
        title = title,
        imojiPath = imojiPath,
        color = color,
    )
}
