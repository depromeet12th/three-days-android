package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.domain.entity.habit.Habit

fun HabitEntity.toHabit(): Habit {
    return Habit(
        habitId = habitId.toLong(),
        memberId = memberId,
        title = title,
        imojiPath = imojiPath,
        dayOfWeeks = dayOfWeeks,
        createdDate = createdDate,
        reward = reward,
        color = color,
        mate = mate,
        habitAchievement = habitAchievement,
    )
}
