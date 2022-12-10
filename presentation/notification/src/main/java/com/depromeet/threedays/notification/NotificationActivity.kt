package com.depromeet.threedays.notification

import android.os.Bundle
import androidx.activity.viewModels
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.notification.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
