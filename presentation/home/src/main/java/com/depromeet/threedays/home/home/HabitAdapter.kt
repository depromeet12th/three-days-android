package com.depromeet.threedays.home.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.home.home.model.HabitUI
import kotlin.reflect.KFunction0

class HabitAdapter(
    private val createHabitAchievement: (HabitUI) -> Unit,
    private val deleteHabitAchievement: (Long, Long) -> Unit,
    private val onCreateHabitClick: KFunction0<Unit>,
    private val onMoreClick: (HabitUI) -> Unit,
) : ListAdapter<HabitUI, RecyclerView.ViewHolder>(DIFF_UTIL) {

    override fun getItemViewType(position: Int): Int {
        return if(position == currentList.size) {
            HABIT_FOOTER_VIEW_TYPE
        } else {
            HABIT_BODY_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == HABIT_BODY_VIEW_TYPE) {
            HabitViewHolder.create(parent, false)
        } else {
            HabitFooterViewHolder.create(parent, false)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HABIT_BODY_VIEW_TYPE) {
            (holder as HabitViewHolder).onBind(
                getItem(position),
                holder.itemView.context,
                createHabitAchievement,
                deleteHabitAchievement,
                onMoreClick
            )
        } else {
            (holder as HabitFooterViewHolder).onBind(
                onCreateHabitClick,
            )
        }
    }

    override fun getItemCount(): Int = currentList.size + 1

    override fun getItemId(position: Int): Long {
        return getItem(position).habitId
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<HabitUI>() {
            override fun areItemsTheSame(oldItem: HabitUI, newItem: HabitUI): Boolean {
                return (oldItem.habitId == newItem.habitId)
            }

            override fun areContentsTheSame(oldItem: HabitUI, newItem: HabitUI): Boolean {
                return oldItem == newItem
            }
        }

        const val HABIT_BODY_VIEW_TYPE = 0
        const val HABIT_FOOTER_VIEW_TYPE = 1
    }
}
