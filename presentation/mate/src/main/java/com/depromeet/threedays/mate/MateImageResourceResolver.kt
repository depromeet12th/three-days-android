package com.depromeet.threedays.mate

import com.depromeet.threedays.core_design_system.R as core_design

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
                // FIXME: 0 레벨 이미지 적용
                0 -> core_design.drawable.bg_mate_level_1
                1 -> core_design.drawable.bg_mate_level_1
                2 -> core_design.drawable.bg_mate_level_2
                3 -> core_design.drawable.bg_mate_level_3
                4 -> core_design.drawable.bg_mate_level_4
                else -> core_design.drawable.bg_mate_level_5
            }
        }

        /**
         * 짝궁 Level 을 입력받아서 Icon ResourceId 응답
         */
        val levelToIconResourceFunction: (Int) -> Int = { level: Int ->
            when (level) {
                0 -> R.drawable.ic_mate_level_1
                1 -> R.drawable.ic_mate_level_2
                2 -> R.drawable.ic_mate_level_3
                3 -> R.drawable.ic_mate_level_4
                4 -> R.drawable.ic_mate_level_5
                else -> R.drawable.ic_mate_level_5
            }
        }

        /**
         * 짝궁 Level 을 입력받아서 lock ResourceId 응답
         */
        val levelToLockResourceFunction: (Int) -> Int = { level: Int ->
            when (level) {
                0 -> R.drawable.ic_lock_level_1
                1 -> R.drawable.ic_lock_level_1
                2 -> R.drawable.ic_lock_level_2
                3 -> R.drawable.ic_lock_level_3
                4 -> R.drawable.ic_lock_level_4
                else -> R.drawable.ic_lock_level_5
            }
        }
    }
}
