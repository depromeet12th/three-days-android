package com.depromeet.threedays.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.databinding.ItemGoalBinding

class GoalViewHolder(private val view: ItemGoalBinding) : RecyclerView.ViewHolder(view.root) {
    fun onBind(goal: Goal, viewModel: HomeViewModel) {
        view.goal = goal
        view.viewModel = viewModel
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
