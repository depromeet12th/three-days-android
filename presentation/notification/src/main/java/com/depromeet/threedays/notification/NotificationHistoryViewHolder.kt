package com.depromeet.threedays.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.notification.databinding.ItemNotificationBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationHistoryViewHolder(
    private val binding: ItemNotificationBinding,
    private val onItemClicked: (NotificationHistoryUI) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(notificationHistoryUI: NotificationHistoryUI) {
        // 데이터 연결해주는 작업
        binding.tvNotificationTitle.text = notificationHistoryUI.title
        binding.tvNotificationContent.text = notificationHistoryUI.content
        binding.tvTime.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        initEvent(notificationHistoryUI = notificationHistoryUI)
    }

    private fun initEvent(notificationHistoryUI: NotificationHistoryUI) {
        binding.clNotificationItem.setOnClickListener {
            onItemClicked(notificationHistoryUI)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
            onItemClicked: (NotificationHistoryUI) -> Unit,
        ): NotificationHistoryViewHolder {
            return NotificationHistoryViewHolder(
                binding = ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent,
                ),
                onItemClicked = onItemClicked,
            )
        }
    }
}
