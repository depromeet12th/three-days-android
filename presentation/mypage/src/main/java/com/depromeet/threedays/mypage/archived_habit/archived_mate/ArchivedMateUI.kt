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
            mateId = mate.mateId.toLong(),
            habitId = 1L,
            nickname = "닉네임닉네임",
            level = mate.level,
        )
    }

    fun getLevelDescription() = "Lv.${level}까지 진화"

    // FIXME: 짝궁 이미지 정하는 규칙 확인하기
    fun resolveImageUrl() = "@drawable/bg_mate_sample"
}
