package com.depromeet.threedays.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.navigator.SignupNavigator
import com.depromeet.threedays.onboarding.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_R

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    @Inject
    lateinit var signupNavigator: SignupNavigator

    private val onboardingViewModel by viewModels<OnboardingViewModel>()

    private val NUM_PAGES = 3 // 페이지 수를 정해둠

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
    }

    private fun initView() {
        binding.vpOnBoarding.adapter = ScreenSlidePagerAdapter(this)
        binding.vpOnBoarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initEvent() {
        binding.vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.viewFirstIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray200_r4)
                binding.viewSecondIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray200_r4)
                binding.viewThirdIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray200_r4)

                when (position) {
                    0 -> {
                        binding.btnNext.text = getString(R.string.next)
                        binding.viewFirstIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray500_r4)
                    }
                    1 -> {
                        binding.btnNext.text = getString(R.string.next)
                        binding.viewSecondIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray500_r4)
                    }
                    else -> {
                        binding.btnNext.text = getString(R.string.start)
                        binding.viewThirdIndicator.setBackgroundResource(core_R.drawable.bg_rect_gray500_r4)
                    }
                }
            }
        })

        binding.btnNext.setOnSingleClickListener {
            binding.vpOnBoarding.run {
                if (currentItem == 2) {
                    onboardingViewModel.writeIsFirstVisitor()
                    startActivity(signupNavigator.intent(this@OnboardingActivity))
                } else {
                    currentItem += 1
                }
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when (position) { // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> {
                    OnboardingFragment.newInstance(
                        title = getString(R.string.first_on_boarding_page_title),
                        content = getString(R.string.first_on_boarding_page_content),
                        onboardingImageResId = R.drawable.on_boarding_after_splash_1,
                    )
                }

                1 -> {
                    OnboardingFragment.newInstance(
                        title = getString(R.string.second_on_boarding_page_title),
                        content = getString(R.string.second_on_boarding_page_content),
                        onboardingImageResId = R.drawable.on_boarding_after_splash_2,
                    )
                }

                else -> {
                    OnboardingFragment.newInstance(
                        title = getString(R.string.third_on_boarding_page_title),
                        content = getString(R.string.third_on_boarding_page_content),
                        onboardingImageResId = R.drawable.on_boarding_after_splash_3,
                    )
                }
            }
        }
    }
}
