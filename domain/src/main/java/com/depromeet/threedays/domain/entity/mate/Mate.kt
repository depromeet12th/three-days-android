package com.depromeet.threedays.domain.entity.mate

import com.depromeet.threedays.domain.entity.RewardHistory

data class Mate(
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
