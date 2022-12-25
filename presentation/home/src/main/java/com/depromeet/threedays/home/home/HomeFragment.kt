package com.depromeet.threedays.home.home

import android.content.Context
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
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.*
import com.depromeet.threedays.domain.key.HABIT_ID
import com.depromeet.threedays.domain.key.RESULT_CREATE
import com.depromeet.threedays.domain.key.RESULT_UPDATE
import com.depromeet.threedays.home.MainActivity
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.databinding.FragmentHomeBinding
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
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

    @Inject
    lateinit var archivedHabitNavigator: ArchivedHabitNavigator

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
        setObserve()
        initView()
        initEvent()
    }

    private fun onCreateHabitClick() {
        addResultLauncher.launch(habitCreateNavigator.intent(requireContext()))
    }

    private fun onEditClick(habitId: Long) {
        val intent = habitUpdateNavigator.intent(requireContext())
        intent.putExtra(HABIT_ID, habitId)
        addResultLauncher.launch(intent)
    }
    
    private fun onNotificationClick() {
        val intent = notificationNavigator.intent(requireContext())
        addResultLauncher.launch(intent)
    }

    private fun initAdapter() {
        habitAdapter = HabitAdapter(
            createHabitAchievement = { habitId, isThirdClap ->
                viewModel.createHabitAchievement(habitId, isThirdClap)
            },
            deleteHabitAchievement = { habitId, habitAchievementId ->
                viewModel.deleteHabitAchievement(
                    habitId = habitId,
                    habitAchievementId = habitAchievementId
                )
            },
            onCreateHabitClick = ::onCreateHabitClick,
            onMoreClick = {
                MoreActionModal
                    .newInstance(
                        habitUI = it,
                        onEditClick = ::onEditClick,
                        onDeleteClick = { viewModel.deleteHabit(it) }
                    )
                    .show(parentFragmentManager, MoreActionModal.TAG)
            }
        )
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

        // TODO: 하드코딩되어있는거 수정 
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val dayOfWeekList = listOf("월", "화", "수", "목", "금", "토", "일")
        binding.tvDate.text = String.format("%02d월%02d일 %s요일", now.monthValue, now.dayOfMonth, dayOfWeekList[now.dayOfWeek.value - 1])
    }

    private fun initEvent() {
        binding.ivNotification.setOnClickListener {
            onNotificationClick()
        }
        binding.clNoGoal.setOnSingleClickListener {
            onCreateHabitClick()
        }
    }

    private fun showToastMessage(context: Context, title: String) {
        ThreeDaysToast().show(
            context = context,
            title = title,
        )
    }

    private fun showDeleteHabitDialog(
        title: String,
        description: String,
        cancelText: String,
        confirmText: String,
        buttonTopMargin: Float,
        onPositiveAction: () -> Unit,
    ) {
        val dialog = ThreeDaysDialogFragment.newInstance(
            DialogInfo.EMPTY.copy(
                onPositiveAction = onPositiveAction,
                title = title,
                description = description,
                cancelText = cancelText,
                confirmText = confirmText,
                buttonTopMargin = buttonTopMargin,
            )
        )
        dialog.show(requireActivity().supportFragmentManager, ThreeDaysDialogFragment.TAG)
    }

    private fun showSnackBar(
        view: View,
        text: String,
        actionText: String,
        onAction: () -> Unit
    ) {
        ThreeDaysSnackBar().show(
            view = view,
            text = text,
            actionText = actionText,
            onAction = onAction
        )
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.habits.collect { list ->
                        habitAdapter.submitList(list.sortedBy { it.createAt })
                        binding.clNoGoal.visibility =
                            if (list.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvGoal.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
                launch {
                    viewModel.uiEffect.collect {
                        when(it) {
                            is UiEffect.ShowToastMessage -> showToastMessage(
                                context = requireContext(),
                                title = getString(it.resId),
                            )
                            is UiEffect.ShowDeleteHabitDialog -> showDeleteHabitDialog(
                                onPositiveAction = {
                                    viewModel.onDeleteHabitClick(
                                        it.habitType,
                                        it.habitId
                                    )
                                },
                                title = getString(it.titleResId),
                                description = it.descriptionResId?.let { getString(it) } ?: String.Empty,
                                cancelText = getString(it.cancelTextResId),
                                confirmText = getString(it.confirmTextResId),
                                buttonTopMargin = it.buttonTopMargin,
                            )
                            is UiEffect.ShowSnackBar -> showSnackBar(
                                view = binding.clTopLayout,
                                text = getString(it.textResId),
                                actionText = getString(it.actionTextResId),
                                onAction = {
                                    addResultLauncher.launch(archivedHabitNavigator.intent(requireContext()))
                                }
                            )
                            UiEffect.ShowClapAnimation -> (requireActivity() as MainActivity).startCongratulateThirdClapAnimation()
                        }
                    }
                }
            }
        }
    }
}
