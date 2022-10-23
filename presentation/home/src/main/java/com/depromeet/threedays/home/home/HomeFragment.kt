package com.depromeet.threedays.home.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import com.depromeet.threedays.navigator.GoalAddNavigator
import com.depromeet.threedays.navigator.GoalUpdateNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModels<HomeViewModel>()
    lateinit var goalAdapter: GoalAdapter

    @Inject
    lateinit var goalAddNavigator: GoalAddNavigator

    @Inject
    lateinit var goalUpdateNavigator: GoalUpdateNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        viewModel.fetchGoals()
        viewModel.setObserve()
        initView()
    }

    private fun onGoalClick(goal: Goal) {
        viewModel.updateGoals(goal)
    }

    private fun onMoreClick(goal: Goal) {
        val modal = EditGoalModal(goal, ::onEditClick, ::onDeleteClick)
        modal.show(parentFragmentManager, EditGoalModal.TAG)
    }

    private fun onEditClick(goal: Goal) {
        // 수정 페이지로 이동
        startActivity(goalUpdateNavigator.intent(requireContext()))
    }

    private fun onDeleteClick(goal: Goal) {
        val dialog = DeleteGoalDialog(requireContext(), goal, ::onDeleteConfirmClick)
        dialog.show()
    }

    private fun onDeleteConfirmClick(goal: Goal) {
        // 삭제 구현
    }

    private fun onCompleteClick(goal: Goal) {
        // 짝심삼일 완료 구현
    }

    private fun initAdapter() {
        goalAdapter = GoalAdapter(::onGoalClick, ::onMoreClick)
    }

    private fun initView() {
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
            // 임시 데이터 만들고 싶을 때 사용할 것 (나중에 삭제할 예정)
            // tempCreateData()
            startActivity(goalAddNavigator.intent(requireContext()))
        }

        binding.btnMakeGoal.setOnClickListener {
            startActivity(goalAddNavigator.intent(requireContext()))
        }
    }

    private fun HomeViewModel.setObserve() {
        goals.observe(viewLifecycleOwner) {
            goalAdapter.submitList(it)
            goalAdapter.notifyDataSetChanged()

            binding.clNoGoal.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvGoal.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
            binding.ivPlus.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
        }
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
