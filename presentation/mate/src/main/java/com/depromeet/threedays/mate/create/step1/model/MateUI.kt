package com.depromeet.threedays.mate.create.step1.model

import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.entity.mate.MateType
import com.depromeet.threedays.mate.MateImageResourceResolver
import com.depromeet.threedays.mate.MateImageResourceResolver.Companion.levelToResourceFunction
import java.time.LocalDateTime

data class MateUI(
    val id: Long,
    val habitId: Long,
    val memberId: Long,
    val title: String,
    val createAt: LocalDateTime,
    val level: Int,
    val reward: Int?,
    val rewardHistory: List<RewardHistoryUI>?,
    val levelUpAt: LocalDateTime?,
    val characterType: MateType,
    val levelUpSectioin: List<Int>?,
    val bubble: String,
    val status: String,
) : MateImageResourceResolver {
    data class RewardHistoryUI(
        val createAt: LocalDateTime,
    )

    override fun resolveMateImageResource(): Int {
        return levelToResourceFunction(level)
    }
}

fun Mate.toMateUI(): MateUI {
    return MateUI(
        characterType = characterType,
        createAt = createAt,
        habitId = habitId,
        id = id,
        level = level,
        levelUpAt = levelUpAt,
        levelUpSectioin = levelUpSection,
        memberId = memberId,
        reward = reward,
        rewardHistory = rewardHistory?.map { it.toRewardHistoryUI() },
        title = title,
        bubble = bubble,
        status = status,
    )
}

fun Mate.RewardHistory.toRewardHistoryUI() = MateUI.RewardHistoryUI(
    createAt = this.createAt,
)
