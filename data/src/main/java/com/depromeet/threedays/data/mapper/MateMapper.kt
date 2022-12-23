package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.domain.entity.mate.Mate

fun MateEntity.toMate(): Mate {
    return Mate(
        characterType = this.characterType,
        createAt = this.createAt,
        habitId = this.habitId,
        id = this.id,
        level = this.level,
        levelUpAt = this.levelUpAt,
        levelUpSection = this.levelUpSection ?: emptyList(),
        memberId = this.memberId,
        reward = this.reward,
        rewardHistory = this.rewardHistory?.map { it.toRewardHistory() },
        title = this.title,
        bubble = bubble,
    )
}
