package com.depromeet.threedays.mypage

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mypage.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel by viewModels<MyPageViewModel>()

}
