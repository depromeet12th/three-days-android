package com.depromeet.threedays.onboarding

import android.os.Bundle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.onboarding.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
