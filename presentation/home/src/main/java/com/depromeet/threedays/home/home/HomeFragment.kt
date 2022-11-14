package com.depromeet.threedays.home.home

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.util.CustomToast
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest
import com.depromeet.threedays.domain.key.GOAL_ID
import com.depromeet.threedays.domain.key.RESULT_CREATE
import com.depromeet.threedays.domain.key.RESULT_MODIFY
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import com.depromeet.threedays.navigator.GoalAddNavigator
import com.depromeet.threedays.navigator.GoalUpdateNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModels<HomeViewModel>()
    lateinit var goalAdapter: GoalAdapter

    @Inject
    lateinit var goalAddNavigator: GoalAddNavigator

    @Inject
    lateinit var goalUpdateNavigator: GoalUpdateNavigator

    private val addResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        when(result.resultCode) {
            RESULT_CREATE -> viewModel.fetchGoals()
            RESULT_MODIFY -> {
                viewModel.fetchGoals()
                CustomToast().showTextToast(
                    requireContext(),
                    resources.getString(R.string.three_day_goal_modify_toast)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        viewModel.fetchGoals()
        viewModel.setObserve()
        initView()
    }

    private fun onGoalClick(goal: Goal) {
        if(goal.clapIndex == 2 && goal.clapChecked) {
            val dialog = CompleteGoalDialog(requireContext(), goal)
            dialog.show()
        }

        val lastAchievementDate = if (goal.clapChecked) {
            ZonedDateTime.now(ZoneId.systemDefault())
        } else {
            ZonedDateTime.now(ZoneId.systemDefault()).minusDays(1)
        }

        val updatedGoal = UpdateGoalRequest (
            goalId = goal.goalId,
            title = goal.title,
            startDate = if(goal.startDate == null) null else goal.startDate,
            endDate = if(goal.endDate == null) null else goal.endDate,
            startTime = if(goal.startTime == null) null else goal.startTime,
            notificationTime = if(goal.notificationTime == null) null else goal.notificationTime,
            notificationContent = goal.notificationContent,
            sequence = goal.sequence + if(goal.clapChecked) 1 else -1,
            clapIndex = goal.clapIndex,
            clapChecked = goal.clapChecked,
            lastAchievementDate = lastAchievementDate,
        )

        viewModel.updateGoals(updatedGoal)
    }

    private fun onMoreClick(goal: Goal) {
        val modal = EditGoalModal(goal, ::onEditClick, ::onDeleteClick)
        modal.show(parentFragmentManager, EditGoalModal.TAG)
    }

    private fun onEditClick(goal: Goal) {
        // 수정 페이지로 이동
        val intent = goalUpdateNavigator.intent(requireContext())
        intent.putExtra(GOAL_ID, goal.goalId)
        addResultLauncher.launch(intent)
    }

    private fun onDeleteClick(goal: Goal) {
        val dialog = DeleteGoalDialog(requireContext(), goal, ::onDeleteConfirmClick)
        dialog.show()
    }

    private fun onDeleteConfirmClick(goal: Goal) {
        viewModel.deleteGoals(goal.goalId)
        viewModel.fetchGoals()
        CustomToast().showTextToast(
            requireContext(),
            resources.getString(R.string.three_day_goal_delete_toast)
        )
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
                    outRect.bottom = dpToPx(14)
                }
            })
        }

        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val dayOfWeekList = listOf("월", "화", "수", "목", "금", "토", "일")
        binding.tvDate.text = String.format("%02d월%02d일 %s요일", now.monthValue, now.dayOfMonth, dayOfWeekList[now.dayOfWeek.value - 1])
    }

    private fun HomeViewModel.setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    goals.collect {
                        goalAdapter.submitList(it)

                        binding.clNoGoal.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvGoal.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), requireContext().resources.displayMetrics).toInt()
    }
}
