package com.depromeet.threedays.mate.create.step1

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.mate.create.step1.model.HabitUI

class ConnectHabitAdapter(
    private val setHabitClickStatus: (HabitUI) -> Unit,
) : ListAdapter<HabitUI, ConnectHabitViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConnectHabitViewHolder.create(
            parent = parent,
            attachToParent = false,
            setHabitClickStatus = setHabitClickStatus,
        )

    override fun onBindViewHolder(holder: ConnectHabitViewHolder, position: Int) {
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
