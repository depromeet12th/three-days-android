package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.mate.MateEntity
import com.depromeet.threedays.domain.entity.mate.Mate

fun MateEntity.toMate(): Mate {
    return Mate(
        characterType = characterType,
        createAt = createAt,
        habitId = habitId,
        id = id,
        level = level,
        levelUpAt = levelUpAt,
        levelUpSectioin = levelUpSectioin,
        memberId = memberId,
        reward = reward,
        rewardHistory = rewardHistory,
        title = title,
        bubble = bubble,
    )
}
