package com.depromeet.threedays.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.notification.databinding.ItemNotificationBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationHistoryViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(notificationUI: NotificationHistoryUI) {
        // 데이터 연결해주는 작업
        binding.tvNotificationTitle.text = notificationUI.title
        binding.tvNotificationContent.text = notificationUI.content
        binding.tvTime.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): NotificationHistoryViewHolder {
            return NotificationHistoryViewHolder(
                ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
