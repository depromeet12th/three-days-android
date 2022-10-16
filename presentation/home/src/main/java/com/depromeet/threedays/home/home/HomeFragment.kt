package com.depromeet.threedays.home.home

import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModels<HomeViewModel>()

    private fun initView() {
        // 우선 임시로 setOnClickListener를 달아놨습니다.
        // 혜인님 레포에 있는 것처럼 setOnSingleClickListener를 만들어서 사용할 지 여부와
        // viewModel에서 클릭리스너를 달아줄지 등을 협의하고 확정되면 추후에 수정하겠습니다.
        binding.btnMakeGoal.setOnClickListener {
            // TODO: 만들기 페이지로 이동
        }
    }
}