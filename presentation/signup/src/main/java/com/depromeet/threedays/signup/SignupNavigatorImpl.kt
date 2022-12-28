package com.depromeet.threedays.signup

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.SignupNavigator
import javax.inject.Inject

internal class SignupNavigatorImpl @Inject constructor() : SignupNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, SignupActivity::class.java)
    }
}
