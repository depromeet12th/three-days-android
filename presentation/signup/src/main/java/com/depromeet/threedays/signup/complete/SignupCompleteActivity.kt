package com.depromeet.threedays.signup.complete

import android.os.Bundle
import androidx.activity.viewModels
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.navigator.HomeNavigator
import com.depromeet.threedays.signup.R
import com.depromeet.threedays.signup.databinding.ActivitySignupCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupCompleteActivity : BaseActivity<ActivitySignupCompleteBinding>(R.layout.activity_signup_complete) {
    private val viewModel by viewModels<SignupCompleteViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateFcmToken()
        initView()
    }

    private fun initView() {
        binding.tvCheck.setOnSingleClickListener {
            val intent = homeNavigator.intent(this)
            startActivity(intent)
            finish()
        }
    }
}
