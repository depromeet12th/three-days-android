package com.depromeet.threedays.mate.create.step1.model

import com.depromeet.threedays.domain.entity.RewardHistory
import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.entity.mate.MateType
import java.time.LocalDateTime

data class MateUI(
    val id: Long,
    val habitId: Long,
    val memberId: Long,
    val title: String,
    val createAt: LocalDateTime,
    val level: Int,
    val reward: Int?,
    val rewardHistory: List<RewardHistory>?,
    val levelUpAt: LocalDateTime?,
    val characterType: MateType,
    val levelUpSectioin: List<Int>?,
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
