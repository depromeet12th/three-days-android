package com.depromeet.threedays.data.entity.mate

import com.depromeet.threedays.data.entity.RewardHistoryEntity
import java.time.LocalDateTime

data class MateEntity(
    val characterType: String,
    val createAt: LocalDateTime,
    val habitId: Long,
    val id: Long,
    val level: Int,
    val levelUpAt: LocalDateTime?,
    val levelUpSection: List<Int>?,
    val memberId: Long,
    val reward: Int,
    val rewardHistory: List<RewardHistoryEntity>?,
    val title: String,
)
