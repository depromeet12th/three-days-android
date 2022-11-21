package com.depromeet.threedays.home.home

import android.graphics.Rect
import android.os.Bundle
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
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.domain.entity.habit.Habit
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
    lateinit var habitAdapter: HabitAdapter

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

    private fun onGoalClick(habit: Habit) {
//        if(habit.clapIndex == 2 && habit.clapChecked) {
//            val dialog = CompleteGoalDialog(requireContext(), habit)
//            dialog.show()
//        }
//
//        val lastAchievementDate = if (habit.clapChecked) {
//            ZonedDateTime.now(ZoneId.systemDefault())
//        } else {
//            ZonedDateTime.now(ZoneId.systemDefault()).minusDays(1)
//        }
//
//        val updatedGoal = UpdateGoalRequest (
//            goalId = habit.goalId,
//            title = habit.title,
//            startDate = if(habit.startDate == null) null else habit.startDate,
//            endDate = if(habit.endDate == null) null else habit.endDate,
//            startTime = if(habit.startTime == null) null else habit.startTime,
//            notificationTime = if(habit.notificationTime == null) null else habit.notificationTime,
//            notificationContent = habit.notificationContent,
//            sequence = habit.sequence + if(habit.clapChecked) 1 else -1,
//            clapIndex = habit.clapIndex,
//            clapChecked = habit.clapChecked,
//            lastAchievementDate = lastAchievementDate,
//        )
//
//        viewModel.updateGoals(updatedGoal)
    }

    private fun onMoreClick(habit: Habit) {
        val modal = EditHabitModal(habit, ::onEditClick, ::onDeleteClick)
        modal.show(parentFragmentManager, EditHabitModal.TAG)
    }

    private fun onEditClick(habit: Habit) {
        // 수정 페이지로 이동
//        val intent = goalUpdateNavigator.intent(requireContext())
//        intent.putExtra(GOAL_ID, habit.goalId)
//        addResultLauncher.launch(intent)
    }

    private fun onDeleteClick(habit: Habit) {
        val dialog = DeleteHabitDialog(requireContext(), habit, ::onDeleteConfirmClick)
        dialog.show()
    }

    private fun onDeleteConfirmClick(habit: Habit) {
//        viewModel.deleteGoals(habit.goalId)
//        viewModel.fetchGoals()
//        CustomToast().showTextToast(
//            requireContext(),
//            resources.getString(R.string.three_day_goal_delete_toast)
//        )
    }

    private fun initAdapter() {
        habitAdapter = HabitAdapter(::onGoalClick, ::onMoreClick)
    }

    private fun initView() {
        binding.rvGoal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 14.dpToPx(requireContext())
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
                    habits.collect {
                        habitAdapter.submitList(it)
                        binding.clNoGoal.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvGoal.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }
}
