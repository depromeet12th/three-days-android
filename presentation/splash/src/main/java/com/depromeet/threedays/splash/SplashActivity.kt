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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.core.analytics.MixPanelEvent
import com.depromeet.threedays.core.analytics.getScreenName
import com.depromeet.threedays.core.analytics.getViewedEventName
import com.depromeet.threedays.navigator.HomeNavigator
import com.depromeet.threedays.navigator.OnboardingNavigator
import com.depromeet.threedays.navigator.SignupNavigator
import com.depromeet.threedays.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    @Inject
    lateinit var signupNavigator: SignupNavigator

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator

    private val splashViewModel by viewModels<SplashViewModel>()
    private var isFirstVisitor = false


    override fun onCreate(savedInstanceState: Bundle?) {
        AnalyticsUtil.event(
            name = getViewedEventName(this),
            properties = mapOf(
                MixPanelEvent.ScreenName to getScreenName(this),
            )
        )

        setObserve()

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
            playAnimation()
        }
    }

    private fun goToHomeOrSignupActivityWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
              val intent = if(isFirstVisitor) {
                  onboardingNavigator.intent(this)
              } else if(viewModel.isSignedUp()) {
                  homeNavigator.intent(this)
              }
              else {
                  signupNavigator.intent(this)
              }
              startActivity(intent)
              finish()
        }, DELAYED_MILLIS)
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    splashViewModel.isFirstVisitor.collect {
                        isFirstVisitor = it
                    }
                }
            }
        }
    }

    companion object {
        private const val DELAYED_MILLIS = 2000L
    }
}
