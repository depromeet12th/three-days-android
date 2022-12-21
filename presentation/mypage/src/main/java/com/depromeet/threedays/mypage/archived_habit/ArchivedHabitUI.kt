package com.depromeet.threedays.mypage.archived_habit

import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.mypage.archived_habit.archived_mate.ArchivedMateUI

data class ArchivedHabitUI(
    val habitId: Long,
    val title: String,
    val mate: ArchivedMateUI?,
    var editable: Boolean,
    var selected: Boolean,
    var mateOpened: Boolean,
) {
    companion object {
        fun from(habit: Habit) = ArchivedHabitUI(
            habitId = habit.id,
            title = habit.title,
            mate = habit.mate?.let { ArchivedMateUI.from(it) },
            editable = false,
            selected = false,
            mateOpened = false,
        )
    }

    fun copyOf(
        editable: Boolean? = null,
        selected: Boolean? = null,
        mateOpened: Boolean? = null,
    ) = ArchivedHabitUI(
        habitId = this.habitId,
        title = this.title,
        mate = this.mate,
        editable = editable ?: this.editable,
        selected = selected ?: this.selected,
        mateOpened = mateOpened ?: this.mateOpened,
    )

    /**
     * 짝궁이 있는 습관인지
     */
    fun hasMate(): Boolean = mate != null
}
