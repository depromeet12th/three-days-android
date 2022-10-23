package com.depromeet.threedays.home.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModels<HomeViewModel>()
    lateinit var goalAdapter: GoalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goalAdapter = GoalAdapter(viewModel)
        viewModel.fetchGoals()
        viewModel.setObserve()
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
            addItemDecoration(object : RecyclerView.ItemDecoration() {
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

        binding.ivPlus.setOnClickListener {
            tempCreateData()
        }
    }

    private fun HomeViewModel.setObserve() {
        goals
            .filter { it.isNotEmpty() }
            .onEach {
                goalAdapter.submitList(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun tempCreateData() {
        viewModel.createGoals(
            Goal(
                1,
                "일어나자마자 물 마시기",
                sequence = 10,
                clapIndex = 1,
                clapChecked = false
            )
        )
        viewModel.createGoals(
            Goal(
                2,
                "코딩테스트 1문제 풀기",
                sequence = 4,
                clapIndex = 0,
                clapChecked = true
            )
        )
        viewModel.createGoals(Goal(3, "샐러드 먹기", sequence = 7, clapIndex = 2, clapChecked = false))
    }
}
