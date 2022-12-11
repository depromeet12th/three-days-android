package com.depromeet.threedays.notification

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.notification.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()
    lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationAdapter = NotificationAdapter()
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notificationAdapter

            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            dividerItemDecoration.setDrawable(context.resources.getDrawable(R.drawable.item_delimiter))
            addItemDecoration(dividerItemDecoration)
        }
        viewModel.fetchNotifications()
        setObserve()
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.apply {
                    notifications.collect {
                        notificationAdapter.submitList(it)
                        binding.containerNotificationEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }
}
