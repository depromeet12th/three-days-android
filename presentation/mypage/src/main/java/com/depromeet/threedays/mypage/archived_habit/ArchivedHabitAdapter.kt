package com.depromeet.threedays.mypage.archived_habit

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ArchivedHabitAdapter(
    private val closeMateUIFunction: (ArchivedHabitUI) -> Unit,
    private val openMateUIFunction: (ArchivedHabitUI) -> Unit,
    private val toggleSelectedFunction: (ArchivedHabitUI) -> Unit,
) : ListAdapter<ArchivedHabitUI, ArchivedHabitViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArchivedHabitViewHolder.create(
            parent = parent,
            attachToParent = false,
            closeMateUIFunction = closeMateUIFunction,
            openMateUIFunction = openMateUIFunction,
            toggleSelectedFunction = toggleSelectedFunction,
        )

    override fun onBindViewHolder(holder: ArchivedHabitViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).habitId
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ArchivedHabitUI>() {
            override fun areItemsTheSame(
                oldItem: ArchivedHabitUI,
                newItem: ArchivedHabitUI,
            ): Boolean {
                return oldItem.habitId == newItem.habitId
            }

            override fun areContentsTheSame(
                oldItem: ArchivedHabitUI,
                newItem: ArchivedHabitUI,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
