package com.depromeet.threedays.create.update

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.HabitUpdateNavigator
import javax.inject.Inject

class HabitUpdateNavigatorImpl @Inject constructor() : HabitUpdateNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, HabitUpdateActivity::class.java)
    }
}
