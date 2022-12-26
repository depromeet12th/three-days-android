package com.depromeet.threedays.policy

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.PolicyNavigator
import javax.inject.Inject

class PolicyNavigatorImpl @Inject constructor() : PolicyNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, PolicyActivity::class.java)
    }
}
