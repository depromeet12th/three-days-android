package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.RewardHistoryEntity
import com.depromeet.threedays.domain.entity.mate.Mate

fun RewardHistoryEntity.toRewardHistory() = Mate.RewardHistory(
    createAt = this.createAt,
)
