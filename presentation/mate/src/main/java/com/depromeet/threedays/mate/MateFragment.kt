package com.depromeet.threedays.mate

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MateFragment: BaseFragment<FragmentMateBinding, MateViewModel>(R.layout.fragment_mate) {
    override val viewModel by viewModels<MateViewModel>()

}
