package com.depromeet.threedays.data.entity.habit

import com.depromeet.threedays.data.entity.mate.MateEntity

class HabitEntity (
    val id: Long,
    val memberId: Int,
    val title: String,
    val imojiPath: String,
    val dayOfWeeks: List<String>,
    val reward: Int,
    val color: String,
    val mate: MateEntity?,
    val totalAchievementCount: Int,
    val todayHabitAchievementId: Long?, // null이면 오늘 체크를 하지 않은 것
    val sequence: Int,
    val createAt: String,
)
