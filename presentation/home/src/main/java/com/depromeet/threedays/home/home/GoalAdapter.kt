package com.depromeet.threedays.home.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.domain.entity.Goal
import kotlin.reflect.KFunction1

class GoalAdapter(
    private val onGoalClick: KFunction1<Goal, Unit>,
    private val onMoreClick: KFunction1<Goal, Unit>
) : ListAdapter<Goal, GoalViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        return GoalViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.onBind(getItem(position), onGoalClick, onMoreClick)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).goalId
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Goal>() {
            override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem.goalId == newItem.goalId
            }

            override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem == newItem
            }
        }
    }
}
