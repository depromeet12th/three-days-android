package com.depromeet.threedays.mate.create.step2

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.FragmentChooseMateTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseMateTypeFragment :
    BaseFragment<FragmentChooseMateTypeBinding, ChooseMateTypeViewModel>(R.layout.fragment_choose_mate_type) {
    override val viewModel by viewModels<ChooseMateTypeViewModel>()

}
