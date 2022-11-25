package com.depromeet.threedays.core.extensions

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

val Int.dp: Int
    @JvmSynthetic inline get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()
