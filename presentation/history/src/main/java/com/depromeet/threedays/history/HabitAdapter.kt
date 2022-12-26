package com.depromeet.threedays.history

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.history.model.HabitUI

class HabitAdapter: ListAdapter<HabitUI, HabitViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder.create(
            parent = parent,
            attachToParent = false,
        )

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).habitId
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
