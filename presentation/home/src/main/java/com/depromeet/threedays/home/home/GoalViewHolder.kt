package com.depromeet.threedays.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.ItemGoalBinding
import kotlin.reflect.KFunction1

class GoalViewHolder(private val view: ItemGoalBinding) : RecyclerView.ViewHolder(view.root) {

    fun onBind(goal: Goal, onGoalClick: KFunction1<Goal, Unit>, onMoreClick: KFunction1<Goal, Unit>) {

        view.ivFirstDay.setOnClickListener {
            switchGoalState(0, goal, onGoalClick, it as ImageButton)
        }
        view.ivSecondDay.setOnClickListener {
            switchGoalState(1, goal, onGoalClick, it as ImageButton)
        }
        view.ivThirdDay.setOnClickListener {
            switchGoalState(2, goal, onGoalClick, it as ImageButton)
        }
        view.ivMore.setOnClickListener {
            onMoreClick(goal)
        }
    }

    private fun switchGoalState(
        clickedIndex: Int,
        goal: Goal,
        onGoalClick: KFunction1<Goal, Unit>,
        view: ImageButton
    ) {
        if (goal.clapIndex == clickedIndex) {
            goal.clapChecked = !goal.clapChecked
            onGoalClick(goal)

            if (goal.clapChecked) {
                view.background = itemView.resources.getDrawable(R.drawable.bg_oval_white, null)
                view.setImageResource(com.depromeet.threedays.core_design_system.R.drawable.ic_hand_done)
            } else {
                view.background = itemView.resources.getDrawable(R.drawable.bg_oval_gray, null)
                view.setImageResource(com.depromeet.threedays.core_design_system.R.drawable.ic_hand_normal)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): GoalViewHolder {
            return GoalViewHolder(
                ItemGoalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
