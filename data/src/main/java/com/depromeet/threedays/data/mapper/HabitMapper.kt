package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.Color

fun HabitEntity.toHabit(): Habit {
    return Habit(
        habitId = habitId,
        memberId = memberId,
        title = title,
        imojiPath = imojiPath,
        dayOfWeeks = dayOfWeeks,
        reward = reward,
        color = getColorFromString(color),
        mate = mate,
        todayHabitAchievementId = todayHabitAchievementId,
        sequence = sequence,
        createAt = createAt,
    )
}

private fun getColorFromString(color: String): Color {
    return when(color) {
        "green" -> Color.GREEN
        "blue" -> Color.BLUE
        "yellow" -> Color.YELLOW
        "red" -> Color.RED
        "pink" -> Color.PINK
        "purple" -> Color.PURPLE
        else -> Color.GREEN
    }
}
