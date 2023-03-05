package com.depromeet.threedays.notification

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.notification.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotificationHistoryActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationHistoryViewModel>()
    lateinit var notificationAdapter: NotificationHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationAdapter = NotificationHistoryAdapter(
            onItemClicked = {
                viewModel.markAsRead(
                    notificationHistoryId = it.id,
                )
            },
        )
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notificationAdapter

            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            dividerItemDecoration.setDrawable(context.resources.getDrawable(R.drawable.item_delimiter))
            addItemDecoration(dividerItemDecoration)
        }
        viewModel.fetchNotifications()
        setObserve()
        initEvent()
    }

    private fun setObserve() {
        viewModel.error
            .onEach { errorMessage -> ThreeDaysToast().error(this, errorMessage) }
            .launchIn(lifecycleScope)

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

    private fun initEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
