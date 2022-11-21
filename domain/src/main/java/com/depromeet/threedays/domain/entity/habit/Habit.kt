package com.depromeet.threedays.domain.entity.habit

import com.depromeet.threedays.domain.entity.HabitAchievement
import com.depromeet.threedays.domain.entity.mate.Mate

data class Habit(
    val habitId: Long,
    val memberId: Int,
    val title: String,
    val imojiPath: String,
    val dayOfWeeks: ArrayList<String>,
    val createdDate: String? = null,
    val reward: Int,
    val color: String,
    val mate: Mate,
    val habitAchievement: HabitAchievement,
)
