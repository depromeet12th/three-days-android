package com.depromeet.threedays.history.model

import com.depromeet.threedays.domain.entity.record.Record

data class RecordUI(
    val achievementCount: Int,
    val frequentHabitUI: FrequentHabitUI?,
    val rewardCount: Int
)

fun Record.toPresentationModel(): RecordUI {
    return RecordUI(
        achievementCount = this.achievementCount,
        frequentHabitUI = this.frequentHabit?.toPresentationModel(),
        rewardCount = this.rewardCount,
    )
}
