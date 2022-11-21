package com.depromeet.threedays.home.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.databinding.ItemHabitBinding
import kotlin.reflect.KFunction1
import com.depromeet.threedays.core_design_system.R
import com.depromeet.threedays.domain.entity.Color

class HabitViewHolder(private val view: ItemHabitBinding) : RecyclerView.ViewHolder(view.root) {

    fun onBind(habit: Habit, onHabitClick: KFunction1<Habit, Unit>, onMoreClick: KFunction1<Habit, Unit>) {

//        view.ivFirstDay.setOnClickListener {
//            switchGoalState(0, habit, onHabitClick, it as ImageButton)
//        }
//        view.ivSecondDay.setOnClickListener {
//            switchGoalState(1, habit, onHabitClick, it as ImageButton)
//        }
//        view.ivThirdDay.setOnClickListener {
//            switchGoalState(2, habit, onHabitClick, it as ImageButton)
//        }

        initView(
            reward = habit.reward,
            dayOfweeks = habit.dayOfWeeks,
            todayIndex = habit.sequence % 3,
            color = habit.color,
            isTodayChecked = (habit.todayHabitAchievementId != null)
        )

        view.ivMore.setOnClickListener {
            onMoreClick(habit)
        }
    }

    private fun initView(
        reward: Int,
        dayOfweeks: ArrayList<String>,
        todayIndex: Int,
        color: Color,
        isTodayChecked: Boolean
    ) {
        view.tvHabitReward.text = "${reward}개"
        view.tvHabitDayOfWeek.text = convertDayListToString(dayOfweeks)

        for (targetIndex in 0..2) {
            if (targetIndex < todayIndex) {
                setCheckedDay(targetIndex, getColorStateList(color))
            } else if (targetIndex == todayIndex) {
                if (isTodayChecked) {
                    setCheckedDay(todayIndex, getColorStateList(color))
                } else {
                    bindButtonAndColor(
                        todayIndex,
                        getLightColorStateList(color),
                        getColorStateList(color)
                    )
                }
            } else {
                val disabledBgColor =
                    ColorStateList.valueOf(itemView.context.getColor(R.color.gray_200))
                val disabledTvColor =
                    ColorStateList.valueOf(itemView.context.getColor(R.color.gray_400))
                bindButtonAndColor(targetIndex, disabledBgColor, disabledTvColor)
            }
        }
    }

    private fun setCheckedDay(targetIndex: Int, color: ColorStateList) {
        when(targetIndex) {
            0 -> {
                view.ivCheckFirstDay.visibility = View.VISIBLE
                view.ivFirstDay.backgroundTintList = color
            }
            1 -> {
                view.ivCheckSecondDay.visibility = View.VISIBLE
                view.ivSecondDay.backgroundTintList = color
            }
            2 -> {
                view.ivCheckThirdDay.visibility = View.VISIBLE
                view.ivThirdDay.backgroundTintList = color
            }
        }
    }

    private fun bindButtonAndColor(index: Int, bgColor: ColorStateList, tvColor: ColorStateList) {
        when(index) {
            0 -> setBackgroundAndTextColor(
                iv = view.ivFirstDay,
                bgColor = bgColor,
                tv = view.tvFirstDay,
                tvColor = tvColor
            )
            1 -> setBackgroundAndTextColor(
                iv = view.ivSecondDay,
                bgColor = bgColor,
                tv = view.tvSecondDay,
                tvColor = tvColor
            )
            2 -> setBackgroundAndTextColor(
                iv = view.ivThirdDay,
                bgColor = bgColor,
                tv = view.tvThirdDay,
                tvColor = tvColor
            )
        }
    }

    private fun setBackgroundAndTextColor(iv: ImageView, bgColor: ColorStateList, tv: TextView, tvColor: ColorStateList) {
        iv.backgroundTintList = bgColor
        tv.setTextColor(tvColor)
        tv.visibility = View.VISIBLE
    }

    private fun switchGoalState(
        clickedIndex: Int,
        habit: Habit,
        onHabitClick: KFunction1<Habit, Unit>,
        view: ImageButton
    ) {
//        if (habit.clapIndex == clickedIndex) {
//            habit.clapChecked = !habit.clapChecked
//            onHabitClick(habit)
//
//            if (habit.clapChecked) {
//                view.background = itemView.resources.getDrawable(R.drawable.bg_oval_white, null)
//                view.setImageResource(com.depromeet.threedays.core_design_system.R.drawable.ic_hand_done)
//            } else {
//                view.background = itemView.resources.getDrawable(R.drawable.bg_oval_gray, null)
//                view.setImageResource(com.depromeet.threedays.core_design_system.R.drawable.ic_hand_normal)
//            }
//        }
    }

    private fun getColorStateList(color: Color): ColorStateList {
        return when(color) {
            Color.GREEN -> ColorStateList.valueOf(itemView.context.getColor(R.color.green_sub_color))
            Color.BLUE -> ColorStateList.valueOf(itemView.context.getColor(R.color.blue_sub_color))
            Color.YELLOW -> ColorStateList.valueOf(itemView.context.getColor(R.color.yellow_sub_color))
            Color.RED -> ColorStateList.valueOf(itemView.context.getColor(R.color.red_sub_color))
            Color.PINK -> ColorStateList.valueOf(itemView.context.getColor(R.color.pink_sub_color))
            Color.PURPLE -> ColorStateList.valueOf(itemView.context.getColor(R.color.purple_sub_color))
        }
    }

    private fun getLightColorStateList(color: Color): ColorStateList {
        return when(color) {
            Color.GREEN -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_green_sub_color))
            Color.BLUE -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_blue_sub_color))
            Color.YELLOW -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_yellow_sub_color))
            Color.RED -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_red_sub_color))
            Color.PINK -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_pink_sub_color))
            Color.PURPLE -> ColorStateList.valueOf(itemView.context.getColor(R.color.light_purple_sub_color))
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

    private fun convertDayListToString(dayOfWeeks: ArrayList<String>): String {
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
