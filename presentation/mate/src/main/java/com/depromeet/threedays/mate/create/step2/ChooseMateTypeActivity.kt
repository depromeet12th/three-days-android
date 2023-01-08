package com.depromeet.threedays.mate.create.step2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.analytics.*
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.create.step3.SetMateNicknameActivity
import com.depromeet.threedays.mate.databinding.ActivityChooseMateTypeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseMateTypeActivity : BaseActivity<ActivityChooseMateTypeBinding>(R.layout.activity_choose_mate_type) {
    private val viewModel by viewModels<ChooseMateTypeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnalyticsUtil.event(
            name = ThreeDaysEvent.MateMakingViewed.toString(),
            properties = mapOf(
                MixPanelEvent.ScreenName to "${Screen.MateMaking}2",
            )
        )

        if(intent.hasExtra("clickedHabit")) {
            val clickedHabit = intent.getParcelableExtra<HabitUI>("clickedHabit")
            viewModel.setClickHabit(clickedHabit!!)
        }

        initEvent()
        setUiStateObserver()
    }

    private fun initEvent() {
        binding.clWhippingMate.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.MateSelected.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to "${Screen.MateMaking}2",
                    MixPanelEvent.ButtonType to ButtonType.Sparta,
                )
            )

            viewModel.setMateType(MateType.WhippingMate)
        }
        binding.clCarrotMate.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.MateSelected.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to "${Screen.MateMaking}2",
                    MixPanelEvent.ButtonType to ButtonType.Carrot,
                )
            )

            viewModel.setMateType(MateType.CarrotMate)
        }
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
        binding.btnNext.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to "${Screen.MateMaking}2",
                    MixPanelEvent.ButtonType to ButtonType.Next,
                )
            )

            val intent = Intent(this, SetMateNicknameActivity::class.java)
            intent.putExtra("clickedHabit", viewModel.habit)

            // TODO: bad code
            intent.putExtra("mateType",
                if (viewModel.uiState.value.mateType == MateType.WhippingMate) {
                    "WHIP"
                } else {
                    "CARROT"
                }
            )
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    private fun setMateSelected(
        whippingMateBackgroundRes: Int,
        carrotMateBackgroundRes: Int,
        whippingMateRes: Int,
        carrotMateRes: Int,
        boxImageResId: Int,
    ) {
        binding.clWhippingMate.setBackgroundResource(whippingMateBackgroundRes)
        binding.clCarrotMate.setBackgroundResource(carrotMateBackgroundRes)
        binding.ivWhippingMateIllustrator.setBackgroundResource(whippingMateRes)
        binding.ivCarrotMateIllustrator.setBackgroundResource(carrotMateRes)
        binding.ivIllustrator.setBackgroundResource(boxImageResId)
    }

    private fun setUiStateObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setMateSelected(
                        whippingMateBackgroundRes = it.whippingMateBackgroundRes,
                        carrotMateBackgroundRes = it.carrotMateBackgroundRes,
                        whippingMateRes = it.whippingMateRes,
                        carrotMateRes = it.carrotMateRes,
                        boxImageResId = it.boxImageResId
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}
