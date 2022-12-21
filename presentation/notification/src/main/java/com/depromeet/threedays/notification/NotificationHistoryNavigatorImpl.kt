package com.depromeet.threedays.notification

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.NotificationNavigator
import javax.inject.Inject

class NotificationHistoryNavigatorImpl @Inject constructor(): NotificationNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, NotificationHistoryActivity::class.java)
    }
}
