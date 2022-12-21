package com.depromeet.threedays.create.create

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.HabitCreateNavigator
import javax.inject.Inject

class HabitCreateNavigatorImpl @Inject constructor() : HabitCreateNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, HabitCreateActivity::class.java)
    }
}
