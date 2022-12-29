package com.depromeet.threedays.mate.model

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.mate.R

data class StampUI(
    val stampCount: Long = 1,
    val color: Color = Color.GREEN,
    val backgroundResId: Int = R.drawable.bg_oval_hand_gray
)
