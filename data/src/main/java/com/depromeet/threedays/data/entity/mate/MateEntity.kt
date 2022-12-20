package com.depromeet.threedays.data.entity.mate

import com.depromeet.threedays.data.entity.RewardHistory

data class MateEntity(
    val characterType: String,
    val createAt: String,
    val habitId: Int,
    val id: Int,
    val level: Int,
    val levelUpAt: String,
    val levelUpSectioin: List<Int>,
    val memberId: Int,
    val reward: Int,
    val rewardHistory: List<RewardHistory>,
    val title: String
)
