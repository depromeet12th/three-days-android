package com.depromeet.threedays.mate.create.step3

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.FragmentSetMateNicknameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetMateNicknameFragment :
    BaseFragment<FragmentSetMateNicknameBinding, SetMateNicknameViewMoodel>(R.layout.fragment_set_mate_nickname) {
    override val viewModel by viewModels<SetMateNicknameViewMoodel>()

}
