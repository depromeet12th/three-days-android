package com.depromeet.threedays.create.update

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.GoalUpdateNavigator
import javax.inject.Inject

class HabitUpdateNavigatorImpl @Inject constructor() : GoalUpdateNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, HabitUpdateActivity::class.java)
    }
}
