package com.depromeet.threedays.notification

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class NotificationAdapter : ListAdapter<NotificationUI, NotificationViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NotificationUI>() {
            override fun areItemsTheSame(oldItem: NotificationUI, newItem: NotificationUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NotificationUI, newItem: NotificationUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}
