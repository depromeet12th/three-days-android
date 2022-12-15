package com.depromeet.threedays.mypage.archived_habit

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import javax.inject.Inject

class ArchivedHabitNavigatorImpl @Inject constructor() : ArchivedHabitNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, ArchivedHabitActivity::class.java)
    }
}
