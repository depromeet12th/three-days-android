package com.depromeet.threedays.statistics

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.statistics.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment: BaseFragment<FragmentStatisticsBinding, StatisticsViewModel>(R.layout.fragment_statistics) {
    override val viewModel by viewModels<StatisticsViewModel>()

}