package com.depromeet.threedays.home.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModels<HomeViewModel>()
    private val goalAdapter = GoalAdapter()
    private val tempList = mutableListOf(
        Goal(0, "이불 정리하기", 1, 2, true),
        Goal(1, "일어나자마자 물 마시기", 3),
        Goal(2, "코딩테스트 1문제 풀기", 2, 1),
        Goal(3, "샐러드 먹기", 10)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        // 우선 임시로 setOnClickListener를 달아놨습니다.
        // 혜인님 레포에 있는 것처럼 setOnSingleClickListener를 만들어서 사용할 지 여부와
        // viewModel에서 클릭리스너를 달아줄지 등을 협의하고 확정되면 추후에 수정하겠습니다.
        binding.btnMakeGoal.setOnClickListener {
            // TODO: 만들기 페이지로 이동
        }

        binding.rvGoal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = goalAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 100
                }
            })
        }
        goalAdapter.submitList(tempList)
    }
}
