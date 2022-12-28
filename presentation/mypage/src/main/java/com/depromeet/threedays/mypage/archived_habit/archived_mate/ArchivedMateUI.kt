package com.depromeet.threedays.mypage.archived_habit.archived_mate

import com.depromeet.threedays.domain.entity.mate.Mate

data class ArchivedMateUI(
    val mateId: Long,
    val habitId: Long,
    val nickname: String,
    val level: Int,
) {
    companion object {
        fun from(mate: Mate) = ArchivedMateUI(
            mateId = mate.id,
            habitId = mate.habitId,
            nickname = mate.title,
            level = mate.level,
        )
    }

    fun getLevelDescription() = "Lv.${level}까지 진화"
}
