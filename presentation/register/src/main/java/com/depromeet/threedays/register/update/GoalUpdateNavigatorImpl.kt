package com.depromeet.threedays.register.update

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.GoalUpdateNavigator
import javax.inject.Inject

class GoalUpdateNavigatorImpl @Inject constructor() : GoalUpdateNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, GoalUpdateActivity::class.java)
    }
}
