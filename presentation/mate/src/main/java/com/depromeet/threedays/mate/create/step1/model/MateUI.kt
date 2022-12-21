package com.depromeet.threedays.mate.create.step1.model

import com.depromeet.threedays.domain.entity.RewardHistory
import com.depromeet.threedays.domain.entity.mate.Mate

data class MateUI(
    val characterType: String,
    val createAt: String,
    val habitId: Long,
    val id: Long,
    val level: Int,
    val levelUpAt: String?,
    val levelUpSectioin: List<Int>?,
    val memberId: Long,
    val reward: Int?,
    val rewardHistory: List<RewardHistory>?,
    val title: String,
    val bubble: String,
)

fun Mate.toMateUI(): MateUI {
    return MateUI(
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
