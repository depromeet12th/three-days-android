package com.depromeet.threedays.domain.entity.record

data class FrequentHabit(
    val achievementCount: Int,
    val color: String,
    val createAt: String,
    val dayOfWeeks: List<String>,
    val id: Int,
    val imojiPath: String,
    val memberId: Int,
    val title: String
)
