package com.depromeet.threedays.data.entity.mate

import com.depromeet.threedays.domain.entity.RewardHistory

data class MateEntity(
    val id: Long,
    val habitId: Long,
    val memberId: Long,
    val title: String,
    val createAt: String,
    val level: Int,
    val reward: Int?,
    val rewardHistory: List<RewardHistory>?,
    val levelUpAt: String?,
    val characterType: String,
    val levelUpSectioin: List<Int>?,
    val bubble: String,
)
