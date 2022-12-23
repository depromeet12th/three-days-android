package com.depromeet.threedays.data.entity.mate

import com.depromeet.threedays.domain.entity.RewardHistory
import com.depromeet.threedays.domain.entity.mate.MateType
import java.time.LocalDateTime

data class MateEntity(
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
    val levelUpSectioin: List<Int>?,
    val bubble: String,
)
