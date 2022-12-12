package com.depromeet.threedays.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.onboarding.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val NUM_PAGES = 3 // 페이지 수를 정해둠

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vpOnBoarding.adapter = ScreenSlidePagerAdapter(this)
        binding.vpOnBoarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> OnboardingFragment.newInstance(
                    title = getString(R.string.first_on_boarding_page_title),
                    content = getString(R.string.first_on_boarding_page_content),
                )
                1 -> OnboardingFragment.newInstance(
                    title = getString(R.string.second_on_boarding_page_title),
                    content = getString(R.string.second_on_boarding_page_content),
                )
                else -> OnboardingFragment.newInstance(
                    title = getString(R.string.third_on_boarding_page_title),
                    content = getString(R.string.third_on_boarding_page_content),
                )
            }
        }
    }
}
