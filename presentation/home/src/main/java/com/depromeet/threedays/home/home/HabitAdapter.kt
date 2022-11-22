package com.depromeet.threedays.home.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.domain.entity.habit.Habit
import kotlin.reflect.KFunction1

class HabitAdapter(
    private val onHabitClick: KFunction1<Habit, Unit>,
    private val onMoreClick: KFunction1<Habit, Unit>
) : ListAdapter<Habit, HabitViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(getItem(position), onHabitClick, onMoreClick)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).habitId.toLong()
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.habitId == newItem.habitId
            }

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem == newItem
            }
        }
    }
}
