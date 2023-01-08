package com.depromeet.threedays.mate.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.core.analytics.ButtonType
import com.depromeet.threedays.core.analytics.MixPanelEvent
import com.depromeet.threedays.core.analytics.ThreeDaysEvent
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.BottomSheetMateOnBoardingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OnBoardingBottomSheet(
    val onWithMateClick: () -> Unit,
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetMateOnBoardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_mate_on_boarding,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewPager() {
        onBoardingAdapter = OnBoardingAdapter()
        binding.vpOnBoarding.run {
            adapter = onBoardingAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    moveIndicator(position)
                    setContentByPosition(position)
                }
            })
        }
    }

    private fun initEvent() {
        binding.ivClose.setOnSingleClickListener {
            dismiss()
        }
        binding.btnNext.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to "MateOnboarding${binding.vpOnBoarding.currentItem + 1}",
                    MixPanelEvent.ButtonType to ButtonType.Next.toString()
                )
            )

            binding.vpOnBoarding.run {
                when (currentItem) {
                    FIRST_PAGE -> currentItem = SECOND_PAGE
                    SECOND_PAGE -> currentItem = THIRD_PAGE
                    THIRD_PAGE -> {
                        dismiss()
                        onWithMateClick()
                    }
                }
            }
        }
    }

    fun moveIndicator(position: Int) {
        binding.ivIndicator1.setImageResource(R.drawable.bg_oval_gray_300_default_dot)
        binding.ivIndicator2.setImageResource(R.drawable.bg_oval_gray_300_default_dot)
        binding.ivIndicator3.setImageResource(R.drawable.bg_oval_gray_300_default_dot)

        when (position) {
            FIRST_PAGE -> binding.ivIndicator1.setImageResource(R.drawable.bg_oval_gray_600_selected_dot)
            SECOND_PAGE -> binding.ivIndicator2.setImageResource(R.drawable.bg_oval_gray_600_selected_dot)
            THIRD_PAGE -> binding.ivIndicator3.setImageResource(R.drawable.bg_oval_gray_600_selected_dot)
        }
    }

    private fun setContentByPosition(position: Int) {
        AnalyticsUtil.event(
            name = ThreeDaysEvent.MateOnboardingViewed.toString(),
            properties = mapOf(
                MixPanelEvent.ScreenName to "MateOnboarding${position+1}"
            )
        )

        val (btnText, title, content) = when (position) {
            FIRST_PAGE -> Triple(
                getString(R.string.next),
                getString(R.string.on_boarding_first_title),
                getString(R.string.on_boarding_first_content)
            )
            SECOND_PAGE -> Triple(
                getString(R.string.next),
                getString(R.string.on_boarding_second_title),
                getString(R.string.on_boarding_second_content)
            )
            else -> Triple(
                getString(R.string.with_mate),
                getString(R.string.on_boarding_third_title),
                getString(R.string.on_boarding_third_content)
            )
        }

        binding.btnNext.text = btnText
        binding.tvOnBoardingTitle.text = title
        binding.tvOnBoardingContent.text = content
    }

    companion object {
        const val TAG = "OnBoardingBottomSheet"
        const val FIRST_PAGE = 0
        const val SECOND_PAGE = 1
        const val THIRD_PAGE = 2
    }
}
