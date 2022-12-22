package com.depromeet.threedays.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.extensions.formatRelatively
import com.depromeet.threedays.domain.entity.notification.NotificationHistoryStatus
import com.depromeet.threedays.notification.databinding.ItemNotificationBinding
import java.time.LocalDateTime
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources

class NotificationHistoryViewHolder(
    private val binding: ItemNotificationBinding,
    private val onItemClicked: (NotificationHistoryUI) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(notificationHistoryUI: NotificationHistoryUI) {
        // 데이터 연결해주는 작업
        binding.tvNotificationTitle.text = notificationHistoryUI.title
        binding.tvNotificationContent.text = notificationHistoryUI.content
        binding.tvTime.text = notificationHistoryUI.createdAt.formatRelatively(LocalDateTime.now())
        binding.clNotificationItem.setBackgroundResource(
            if (notificationHistoryUI.status == NotificationHistoryStatus.CHECKED) {
                CoreDesignSystemResources.color.white
            } else {
                CoreDesignSystemResources.color.gray_100
            }
        )
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
