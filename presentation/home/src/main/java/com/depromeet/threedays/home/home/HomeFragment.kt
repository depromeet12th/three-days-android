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
import com.depromeet.threedays.core.util.DialogInfo
import com.depromeet.threedays.core.util.ThreeDaysDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.key.HABIT_ID
import com.depromeet.threedays.domain.key.RESULT_CREATE
import com.depromeet.threedays.domain.key.RESULT_UPDATE
import com.depromeet.threedays.domain.util.EmojiUtil
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import com.depromeet.threedays.navigator.HabitCreateNavigator
import com.depromeet.threedays.navigator.HabitUpdateNavigator
import com.depromeet.threedays.navigator.NotificationNavigator
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
    lateinit var habitCreateNavigator: HabitCreateNavigator

    @Inject
    lateinit var habitUpdateNavigator: HabitUpdateNavigator

    @Inject
    lateinit var notificationNavigator: NotificationNavigator

    private val addResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        when(result.resultCode) {
            RESULT_CREATE -> viewModel.fetchGoals()
            RESULT_UPDATE -> {
                viewModel.fetchGoals()
                ThreeDaysToast().show(
                    requireContext(),
                    resources.getString(com.depromeet.threedays.core.R.string.toast_habit_modify_complete)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        viewModel.fetchGoals()
        viewModel.setObserve()
        setUiEffectObserve()
        initView()
        initEvent()
    }

    private fun onGoalClick(habitId: Long) {
//        if(habit.clapIndex == 2 && habit.clapChecked) {
//            val dialog = CompleteGoalDialog(requireContext(), habit)
//            dialog.show()
//        }

        //viewModel.updateGoals(updatedGoal)
    }

    private fun onMoreClick(habitId: Long) {
        val modal = EditHabitModal(habitId, ::onEditClick, ::onDeleteClick)
        modal.show(parentFragmentManager, EditHabitModal.TAG)
    }

    private fun onEditClick(habitId: Long) {
        val intent = habitUpdateNavigator.intent(requireContext())
        intent.putExtra(HABIT_ID, habitId)
        addResultLauncher.launch(intent)
    }

    private fun onDeleteClick(habitId: Long) {
        val dialog = ThreeDaysDialogFragment.newInstance(
            DialogInfo.EMPTY.copy(
                onPositiveAction = {
                    viewModel.deleteGoals(habitId)
                },
                emoji = Emoji.from(EmojiUtil.Word.TRASH).value,
                title = getString(R.string.delete_dialog_title),
                cancelText = getString(R.string.delete_dialog_cancel_text),
                confirmText = getString(R.string.delete_dialog_confirm_text),
            )
        )
        dialog.show(requireActivity().supportFragmentManager, ThreeDaysDialogFragment.TAG)
    }
    
    private fun onNotificationClick() {
        val intent = notificationNavigator.intent(requireContext())
        addResultLauncher.launch(intent)
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

    private fun initEvent() {
        binding.ivNotification.setOnClickListener {
            //onNotificationClick()
            /**
             * 임시로 알림버튼 클릭 했을 때 습관 생성 화면으로 넘어갑니다 */
            val intent = habitCreateNavigator.intent(requireContext())
            addResultLauncher.launch(intent)
        }
    }

    private fun HomeViewModel.setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    habits.collect { list ->
                        habitAdapter.submitList(list.sortedBy { it.createAt })
                        binding.clNoGoal.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvGoal.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setUiEffectObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEffect.collect { uiEffect ->
                    when(uiEffect) {
                        HomeViewModel.UiEffect.DeleteDialog ->  {
                            ThreeDaysToast().show(
                                requireContext(),
                                resources.getString(R.string.habit_delete_success_message)
                            )
                        }
                    }
                }
            }
        }
    }
}
