package com.depromeet.threedays.mypage.archived_habit.archived_mate

import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.mate.MateImageResourceResolver

data class ArchivedMateUI(
    val mateId: Long,
    val habitId: Long,
    val nickname: String,
    val level: Int,
) : MateImageResourceResolver {
    companion object {
        fun from(mate: Mate) = ArchivedMateUI(
            mateId = mate.id,
            habitId = mate.habitId,
            nickname = mate.title,
            level = mate.level,
        )
    }

    fun getLevelDescription() = "Lv.${level}까지 진화"

    /**
     * 짝궁 레벨에 맞는 resource id
     */
    override fun resolveMateImageResource() =
        MateImageResourceResolver.levelToResourceFunction(level)
}
