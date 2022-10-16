package com.depromeet.threedays.my

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.my.databinding.FragmentMyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment: BaseFragment<FragmentMyBinding, MyViewModel>(R.layout.fragment_my) {
    override val viewModel by viewModels<MyViewModel>()

}