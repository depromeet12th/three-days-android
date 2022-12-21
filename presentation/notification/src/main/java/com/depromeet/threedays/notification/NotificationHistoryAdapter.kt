package com.depromeet.threedays.notification

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class NotificationHistoryAdapter : ListAdapter<NotificationHistoryUI, NotificationHistoryViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHistoryViewHolder {
        return NotificationHistoryViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: NotificationHistoryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NotificationHistoryUI>() {
            override fun areItemsTheSame(oldItem: NotificationHistoryUI, newItem: NotificationHistoryUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NotificationHistoryUI, newItem: NotificationHistoryUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}
