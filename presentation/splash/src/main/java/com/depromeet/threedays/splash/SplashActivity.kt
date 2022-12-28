package com.depromeet.threedays.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.navigator.HomeNavigator
import com.depromeet.threedays.navigator.SignupNavigator
import com.depromeet.threedays.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    @Inject
    lateinit var signupNavigator: SignupNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        // onCreate 보다 먼저 호출해야함
        val splashScreen = installSplashScreen()
        if (SDK_INT < Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener {
                playAnimation()
                it.remove()
                goToHomeOrSignupActivityWithDelay()
            }
        }
        super.onCreate(savedInstanceState)
        // XXX: API 31 기기에서 스플래시 스크린 안넘어가는 현상 임시 해결
        if (SDK_INT >= Build.VERSION_CODES.S) {
            playAnimation()
            goToHomeOrSignupActivityWithDelay()
        }
    }

    private fun playAnimation() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        binding.animationView.apply {
            layoutParams.width = (width * 0.61).toInt() // 220/360 = 0.611
            layoutParams.height = layoutParams.width
            setAnimation(R.raw.lottie_splash)
            repeatCount = 1
            playAnimation()
        }
    }

    private fun goToHomeOrSignupActivityWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            if(viewModel.isSignedUp()) {
                startActivity(homeNavigator.intent(applicationContext))
            }
            else {
                startActivity(signupNavigator.intent(applicationContext))
            }
            finish()
        }, DELAYED_MILLIS)
    }

    companion object {
        private const val DELAYED_MILLIS = 1000L
    }
}
