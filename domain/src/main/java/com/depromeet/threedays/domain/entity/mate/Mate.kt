package com.depromeet.threedays.domain.entity.mate

import com.depromeet.threedays.domain.entity.RewardHistory
import java.time.LocalDateTime

data class Mate(
    val id: Long,
    val habitId: Long,
    val memberId: Long,
    val title: String,
    val createAt: LocalDateTime,
    val level: Int,
    val reward: Int,
    val rewardHistory: List<RewardHistory>?,
    val levelUpAt: LocalDateTime?,
    val characterType: MateType,
    val levelUpSectioin: List<Int>,
    val bubble: String,
)
