package com.depromeet.threedays.history.model

import com.depromeet.threedays.domain.entity.record.FrequentHabit

data class FrequentHabitUI(
    val achievementCount: Int,
    val color: String,
    val createAt: String,
    val dayOfWeeks: List<String>,
    val id: Int,
    val imojiPath: String,
    val memberId: Int,
    val title: String
)

fun FrequentHabit.toPresentationModel(): FrequentHabitUI {
    return FrequentHabitUI(
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
