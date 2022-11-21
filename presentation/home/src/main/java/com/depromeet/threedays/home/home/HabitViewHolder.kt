package com.depromeet.threedays.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.databinding.ItemHabitBinding
import kotlin.reflect.KFunction1

class HabitViewHolder(private val view: ItemHabitBinding) : RecyclerView.ViewHolder(view.root) {

    fun onBind(habit: Habit, onHabitClick: KFunction1<Habit, Unit>, onMoreClick: KFunction1<Habit, Unit>) {

        view.ivFirstDay.setOnClickListener {
            switchGoalState(0, habit, onHabitClick, it as ImageButton)
        }
        view.ivSecondDay.setOnClickListener {
            switchGoalState(1, habit, onHabitClick, it as ImageButton)
        }
        view.ivThirdDay.setOnClickListener {
            switchGoalState(2, habit, onHabitClick, it as ImageButton)
        }
        view.ivMore.setOnClickListener {
            onMoreClick(habit)
        }
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
