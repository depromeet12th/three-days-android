package com.depromeet.threedays.mate

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MateFragment: BaseFragment<FragmentMateBinding, MateViewModel>(R.layout.fragment_mate) {
    override val viewModel by viewModels<MateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.ivTooltip.setOnSingleClickListener {
            binding.clTooltip.apply {
                visibility = if (isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
    }
}
