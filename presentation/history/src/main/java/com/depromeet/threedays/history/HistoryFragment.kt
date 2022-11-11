package com.depromeet.threedays.history

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.history.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel by viewModels<HistoryViewModel>()

}
