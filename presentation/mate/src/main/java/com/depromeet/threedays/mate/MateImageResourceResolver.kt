package com.depromeet.threedays.mate

import com.depromeet.threedays.core_design_system.R

/**
 * 짝궁 이미지를 선택한다.
 */
interface MateImageResourceResolver {
    fun resolveMateImageResource(): Int

    companion object {
        /**
         * 짝궁 Level 을 입력받아서 ResourceId 응답
         */
        val levelToResourceFunction: (Int) -> Int = { level: Int ->
            when (level) {
                1 -> R.drawable.bg_mate_level_1
                2 -> R.drawable.bg_mate_level_2
                3 -> R.drawable.bg_mate_level_3
                4 -> R.drawable.bg_mate_level_4
                else -> R.drawable.bg_mate_level_5
            }
        }
    }
}
