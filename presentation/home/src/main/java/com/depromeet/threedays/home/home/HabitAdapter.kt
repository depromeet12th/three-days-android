package com.depromeet.threedays.home.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.home.home.model.HabitUI
import kotlin.reflect.KFunction1

class HabitAdapter(
    private val onHabitClick: KFunction1<Int, Unit>,
    private val onMoreClick: KFunction1<Int, Unit>
) : ListAdapter<HabitUI, HabitViewHolder>(DIFF_UTIL) {

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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<HabitUI>() {
            override fun areItemsTheSame(oldItem: HabitUI, newItem: HabitUI): Boolean {
                return oldItem.habitId == newItem.habitId
            }

            override fun areContentsTheSame(oldItem: HabitUI, newItem: HabitUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}
