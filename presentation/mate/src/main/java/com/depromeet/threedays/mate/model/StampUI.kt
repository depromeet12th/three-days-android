package com.depromeet.threedays.mate.model

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.mate.R

data class StampUI(
    val stampCount: Long = 1,
    val stampType: StampType = StampType.UnStamp,
    val color: Color = Color.GREEN,
    val backgroundResId: Int = R.drawable.bg_oval_hand_gray
)

sealed interface StampType {
    object UnStamp : StampType
    object ColorStamp : StampType
    object Locked : StampType
    object Character : StampType
}
