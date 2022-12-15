package com.depromeet.threedays.mate.create.step2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core_design_system.R as core_design
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.FragmentChooseMateTypeBinding
import com.depromeet.threedays.mate.create.step2.ChooseMateTypeViewModel.MateType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseMateTypeFragment :
    BaseFragment<FragmentChooseMateTypeBinding, ChooseMateTypeViewModel>(R.layout.fragment_choose_mate_type) {
    override val viewModel by viewModels<ChooseMateTypeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        setUiStateObserver()
    }

    private fun initEvent() {
        binding.clWhippingMate.setOnSingleClickListener {
            viewModel.setMateType(MateType.WHIPPING)
        }
        binding.clCarrotMate.setOnSingleClickListener {
            viewModel.setMateType(MateType.CARROT)
        }
    }

    private fun setMateSelected(mateType: MateType) {
        when(mateType) {
            MateType.WHIPPING -> {
                binding.clWhippingMate.setBackgroundResource(R.drawable.bg_rect_gray_200_border_gray_400_r10)
                binding.clCarrotMate.setBackgroundResource(core_design.drawable.bg_rect_gray200_r10)
            }
            MateType.CARROT -> {
                binding.clWhippingMate.setBackgroundResource(core_design.drawable.bg_rect_gray200_r10)
                binding.clCarrotMate.setBackgroundResource(R.drawable.bg_rect_gray_200_border_gray_400_r10)
            }
        }
    }

    private fun setUiStateObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    setMateSelected(uiState.mateType)
                }
            }
        }
    }
}
