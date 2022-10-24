package com.depromeet.threedays.register.add

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.GoalAddNavigator
import javax.inject.Inject

class GoalAddNavigatorImpl @Inject constructor() : GoalAddNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, GoalAddActivity::class.java)
    }
}
