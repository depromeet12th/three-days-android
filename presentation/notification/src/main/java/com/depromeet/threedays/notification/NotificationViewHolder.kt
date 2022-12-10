package com.depromeet.threedays.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.notification.databinding.ItemNotificationBinding

class NotificationViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(notificationUI: NotificationUI) {
        // 데이터 연결해주는 작업
        // binding.tvNotificationContent.text = notificationUI.title
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): NotificationViewHolder {
            return NotificationViewHolder(
                ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
