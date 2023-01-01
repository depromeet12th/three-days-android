package com.depromeet.threedays.mate.create.step1

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.ConnectHabitNavigator
import javax.inject.Inject

class ConnectHabitNavigatorImpl @Inject constructor() : ConnectHabitNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, ConnectHabitActivity::class.java)
    }
}
