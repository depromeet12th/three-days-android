package com.depromeet.threedays.create.create

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.GoalAddNavigator
import javax.inject.Inject

class HabitCreateNavigatorImpl @Inject constructor() : GoalAddNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, HabitCreateActivity::class.java)
    }
}
