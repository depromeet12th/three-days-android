package com.depromeet.threedays.mate.create.step1.model

import android.os.Parcelable
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.Habit

@kotlinx.parcelize.Parcelize
data class HabitUI(
    val habitId: Long,
    val title: String,
    val imojiPath: String,
    val color: Color,
    val clicked: Boolean = false
) : Parcelable

fun Habit.toHabitUI(): HabitUI {
    return HabitUI(
        habitId = id,
        title = title,
        imojiPath = imojiPath,
        color = color,
    )
}
