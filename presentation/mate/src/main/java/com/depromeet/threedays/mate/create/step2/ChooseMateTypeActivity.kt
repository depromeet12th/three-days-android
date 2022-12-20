package com.depromeet.threedays.mate.create.step2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.ActivityChooseMateTypeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseMateTypeActivity : BaseActivity<ActivityChooseMateTypeBinding>(R.layout.activity_choose_mate_type) {
    private val viewModel by viewModels<ChooseMateTypeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
        setUiStateObserver()
    }

    private fun initEvent() {
        binding.clWhippingMate.setOnSingleClickListener {
            viewModel.setMateType(ChooseMateTypeViewModel.MateType.WHIPPING)
        }
        binding.clCarrotMate.setOnSingleClickListener {
            viewModel.setMateType(ChooseMateTypeViewModel.MateType.CARROT)
        }
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
    }

    private fun setMateSelected(mateType: ChooseMateTypeViewModel.MateType) {
        when(mateType) {
            ChooseMateTypeViewModel.MateType.WHIPPING -> {
                binding.clWhippingMate.setBackgroundResource(R.drawable.bg_rect_gray_200_border_gray_400_r10)
                binding.clCarrotMate.setBackgroundResource(com.depromeet.threedays.core_design_system.R.drawable.bg_rect_gray200_r10)
            }
            ChooseMateTypeViewModel.MateType.CARROT -> {
                binding.clWhippingMate.setBackgroundResource(com.depromeet.threedays.core_design_system.R.drawable.bg_rect_gray200_r10)
                binding.clCarrotMate.setBackgroundResource(R.drawable.bg_rect_gray_200_border_gray_400_r10)
            }
        }
    }

    private fun setUiStateObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    setMateSelected(uiState.mateType)
                }
            }
        }
    }
}
