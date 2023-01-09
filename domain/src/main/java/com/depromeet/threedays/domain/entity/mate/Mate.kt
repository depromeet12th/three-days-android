package com.depromeet.threedays.domain.entity.mate

import java.time.LocalDateTime

data class Mate(
    val id: Long,
    val habitId: Long,
    val memberId: Long,
    val title: String,
    val createAt: LocalDateTime?, // TODO: null로 들어와서 임시 조치
    val level: Int,
    val reward: Int,
    val rewardHistory: List<RewardHistory>?,
    val levelUpAt: LocalDateTime?,
    val characterType: MateType,
    val levelUpSection: List<Int>,
    val bubble: String,
    val status: String,
) {
    data class RewardHistory(
        val createAt: LocalDateTime?,
    )
}
