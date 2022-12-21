package com.depromeet.threedays.domain.entity.mate

import java.time.LocalDateTime

data class Mate(
    val characterType: String,
    val createAt: LocalDateTime,
    val habitId: Long,
    val id: Long,
    val level: Int,
    val levelUpAt: LocalDateTime?,
    val levelUpSection: List<Int>,
    val memberId: Long,
    val reward: Int,
    val rewardHistory: List<RewardHistory>?,
    val title: String
) {
    data class RewardHistory(
        val createAt: LocalDateTime,
    )
}
