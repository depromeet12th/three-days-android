package com.depromeet.threedays.license

import android.content.Context
import android.content.Intent
import com.depromeet.threedays.navigator.LicenseNavigator
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class LicenseNavigatorImpl : LicenseNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, OssLicensesMenuActivity::class.java)
    }
}
