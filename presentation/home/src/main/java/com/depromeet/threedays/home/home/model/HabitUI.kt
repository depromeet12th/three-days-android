package com.depromeet.threedays.home.home.model

import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.R
import com.depromeet.threedays.core_design_system.R as core_color

data class HabitUI(
    val habitId: Long,
    val title: String,
    val imojiPath: String,
    val dayOfWeeks: List<String>,
    val reward: Int,
    val todayHabitAchievementId: Long?, // null이면 오늘 체크를 하지 않은 것
    val createAt: String,
    val todayIndex: Int,
    val isTodayChecked: Boolean,
    val checkedBackgroundResId: Int,
    val checkableBackgroundResId: Int,
    val checkableTextColor: Int
)

fun Habit.toHabitUI(): HabitUI {
    return HabitUI(
        habitId = this.habitId,
        title = this.title,
        imojiPath = this.imojiPath,
        dayOfWeeks = this.dayOfWeeks,
        reward = this.reward,
        todayHabitAchievementId = this.todayHabitAchievementId,
        createAt = this.createAt,
        todayIndex = this.sequence % 3,
        isTodayChecked = this.todayHabitAchievementId != null,
        checkedBackgroundResId = when (this.color) {
            Color.GREEN -> R.drawable.selector_check_green
            Color.BLUE -> R.drawable.selector_check_blue
            Color.PINK -> R.drawable.selector_check_pink
        },
        checkableBackgroundResId = when (this.color) {
            Color.GREEN -> R.drawable.selector_check_light_green
            Color.BLUE -> R.drawable.selector_check_light_blue
            Color.PINK -> R.drawable.selector_check_light_pink
        },
        checkableTextColor = when (this.color) {
            Color.GREEN -> core_color.color.green_50
            Color.BLUE -> core_color.color.blue_50
            Color.PINK -> core_color.color.pink_50
        },
    )
}
