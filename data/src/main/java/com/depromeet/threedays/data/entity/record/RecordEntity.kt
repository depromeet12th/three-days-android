package com.depromeet.threedays.data.entity.record

import com.depromeet.threedays.domain.entity.record.Record

data class RecordEntity(
    val achievementCount: Int,
    val frequentHabitEntity: FrequentHabitEntity?,
    val rewardCount: Int
)

fun RecordEntity.toDomainModel(): Record {
    return Record(
        achievementCount = this.achievementCount,
        frequentHabit = this.frequentHabitEntity?.toDomainModel(),
        rewardCount = this.rewardCount,
    )
}
