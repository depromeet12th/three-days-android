package com.depromeet.threedays.mate.create

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.FragmentConnectHabitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectHabitFragment :
    BaseFragment<FragmentConnectHabitBinding, ConnectHabitViewModel>(R.layout.fragment_connect_habit) {
    override val viewModel by viewModels<ConnectHabitViewModel>()

}
