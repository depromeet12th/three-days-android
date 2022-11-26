package com.depromeet.threedays.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.extensions.switchVisible
import com.depromeet.threedays.history.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.ivTooltip.setOnClickListener {
            binding.cvTooltip.switchVisible()
        }
    }
}
