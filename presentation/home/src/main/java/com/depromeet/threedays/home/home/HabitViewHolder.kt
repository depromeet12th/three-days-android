package com.depromeet.threedays.home.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core_design_system.R
import com.depromeet.threedays.home.databinding.ItemHabitBinding
import com.depromeet.threedays.home.home.model.HabitUI
import kotlin.reflect.KFunction1

class HabitViewHolder(private val view: ItemHabitBinding) : RecyclerView.ViewHolder(view.root) {
    lateinit var context: Context

    fun onBind(
        habitUI: HabitUI,
        context: Context,
        createHabitAchievement: KFunction1<Long, Unit>,
        deleteHabitAchievement: (Long, Long) -> Unit,
        onMoreClick: KFunction1<Long, Unit>
    ) {
        this.context = context
        initView(habitUI)
        initEvent(habitUI, createHabitAchievement, deleteHabitAchievement, onMoreClick)
    }

    private fun initView(habitUI: HabitUI) {
        view.habitUI = habitUI
        habitUI.run {
            view.tvHabitDayOfWeek.text = convertDayListToString(dayOfWeeks)

            for (targetIndex in 0..2) {
                if (targetIndex < todayIndex) {
                    setCheckedButton(
                        targetIndex = targetIndex,
                        resId = checkedBackgroundResId
                    )
                } else if (targetIndex == todayIndex) {
                    if (isTodayChecked) {
                        setCheckedButton(
                            targetIndex = targetIndex,
                            resId = checkedBackgroundResId
                        )
                    } else {
                        setUncheckedButton(
                            targetIndex = targetIndex,
                            resId = checkableBackgroundResId,
                            textColor = checkableTextColor
                        )
                    }
                } else {
                    setUncheckedButton(
                        targetIndex = targetIndex,
                        resId = R.drawable.bg_oval_gray,
                        textColor = R.color.gray_400
                    )
                }
            }
        }
    }

    private fun setCheckedButton(targetIndex: Int, resId: Int) {
        setButtonBackground(targetIndex, resId)
        setTextVisibility(targetIndex, isCheckedButton = true)
    }

    private fun setUncheckedButton(targetIndex: Int, resId: Int, textColor: Int) {
        setButtonBackground(targetIndex, resId)
        setTextColor(targetIndex, textColor)
        setTextVisibility(targetIndex, isCheckedButton = false)
    }

    private fun setButtonBackground(targetIndex: Int, resId: Int) {
        when(targetIndex) {
            0 -> view.ivFirstDay.setImageResource(resId)
            1 -> view.ivSecondDay.setImageResource(resId)
            2 -> view.ivThirdDay.setImageResource(resId)
        }
    }

    private fun setTextColor(targetIndex: Int, resId: Int) {
        when(targetIndex) {
            0 -> view.tvFirstDay.setTextColor(context.getColor(resId))
            1 -> view.tvSecondDay.setTextColor(context.getColor(resId))
            2 -> view.tvThirdDay.setTextColor(context.getColor(resId))
        }
    }

    private fun setTextVisibility(targetIndex: Int, isCheckedButton: Boolean) {
        when(targetIndex) {
            0 -> view.tvFirstDay.isVisible = isCheckedButton.not()
            1 -> view.tvSecondDay.isVisible = isCheckedButton.not()
            2 -> view.tvThirdDay.isVisible = isCheckedButton.not()
        }
    }

    private fun convertEnglishDayToKoreanDay(englishDay: String): String {
        return when(englishDay) {
            "MONDAY" -> "월"
            "TUESDAY" -> "화"
            "WEDNESDAY" -> "수"
            "THURSDAY" -> "목"
            "FRIDAY" -> "금"
            "SATURDAY" -> "토"
            "SUNDAY" -> "일"
            else -> "?"
        }
    }

    private fun convertDayListToString(dayOfWeeks: List<String>): String {
        return if (dayOfWeeks.size == 7) {
            "매일"
        } else if (dayOfWeeks.size == 5
            && dayOfWeeks.contains("SATURDAY").not()
            && dayOfWeeks.contains("SUNDAY").not()
        ) {
            "평일"
        } else {
            var str = ""
            dayOfWeeks.forEach {
                str += "${convertEnglishDayToKoreanDay(it)},"
            }
            str.removeSuffix(",")
        }
    }

    private fun initEvent(
        habitUI: HabitUI,
        onHabitClick: KFunction1<Long, Unit>,
        deleteHabitAchievement: (Long, Long) -> Unit,
        onMoreClick: KFunction1<Long, Unit>
    ) {
        view.ivMore.setOnClickListener {
            onMoreClick(habitUI.habitId)
        }

        view.ivFirstDay.setOnClickListener {
            switchHabitState(0, habitUI, onHabitClick, deleteHabitAchievement)
        }
        view.ivSecondDay.setOnClickListener {
            switchHabitState(1, habitUI, onHabitClick, deleteHabitAchievement)
        }
        view.ivThirdDay.setOnClickListener {
            switchHabitState(2, habitUI, onHabitClick, deleteHabitAchievement)
        }
    }

    private fun switchHabitState(
        clickedIndex: Int,
        habitUI: HabitUI,
        createHabitAchievement: KFunction1<Long, Unit>,
        deleteHabitAchievement: (Long, Long) -> Unit,
    ) {
        val isTodayClicked = clickedIndex == habitUI.todayIndex

        if (isTodayClicked) {
            if (habitUI.isTodayChecked) {
                deleteHabitAchievement(
                    habitUI.habitId,
                    habitUI.todayHabitAchievementId ?: -1,
                )
                setUncheckedButton(
                    targetIndex = clickedIndex,
                    resId = habitUI.checkableBackgroundResId,
                    textColor = habitUI.checkableTextColor
                )
            } else {
                createHabitAchievement(habitUI.habitId)
                setCheckedButton(
                    targetIndex = clickedIndex,
                    resId = habitUI.checkedBackgroundResId
                )
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): HabitViewHolder {

            return HabitViewHolder(
                ItemHabitBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
