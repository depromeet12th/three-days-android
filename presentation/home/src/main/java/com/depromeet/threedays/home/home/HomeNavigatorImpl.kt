package com.depromeet.threedays.home.home

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.home.MainActivity
import com.depromeet.threedays.navigator.HomeNavigator
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }
}