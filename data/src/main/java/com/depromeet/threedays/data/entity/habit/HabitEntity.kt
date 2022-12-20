package com.depromeet.threedays.data.entity.habit

import com.depromeet.threedays.domain.entity.mate.Mate

class HabitEntity (
    val habitId: Long,
    val memberId: Int,
    val title: String,
    val imojiPath: String,
    val dayOfWeeks: List<String>,
    val reward: Int,
    val color: String,
    val mate: Mate?,
    val todayHabitAchievementId: Long?, // null이면 오늘 체크를 하지 않은 것
    val sequence: Int,
    val createAt: String,
)
