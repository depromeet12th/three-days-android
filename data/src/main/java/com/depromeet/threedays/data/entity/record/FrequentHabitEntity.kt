package com.depromeet.threedays.data.entity.record

import com.depromeet.threedays.domain.entity.record.FrequentHabit

data class FrequentHabitEntity(
    val achievementCount: Int,
    val color: String,
    val createAt: String,
    val dayOfWeeks: List<String>,
    val id: Int,
    val imojiPath: String,
    val memberId: Int,
    val title: String
)

fun FrequentHabitEntity.toDomainModel(): FrequentHabit {
    return FrequentHabit(
        achievementCount = this.achievementCount,
        color = this.color,
        createAt = this.createAt,
        dayOfWeeks = this.dayOfWeeks,
        id = this.id,
        imojiPath = this.imojiPath,
        memberId = this.memberId,
        title = this.title,
    )
}
