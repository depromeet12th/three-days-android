package com.depromeet.threedays.history

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.extensions.gone
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.create.create.HabitCreateActivity
import com.depromeet.threedays.domain.key.HABIT_ID
import com.depromeet.threedays.domain.util.EmojiUtil
import com.depromeet.threedays.history.databinding.FragmentHistoryBinding
import com.depromeet.threedays.history.detail.DetailHistoryActivity
import com.depromeet.threedays.history.model.HabitUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.depromeet.threedays.core_design_system.R as core_design

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel by viewModels<HistoryViewModel>()
    lateinit var habitAdapter: HabitAdapter

    override fun onResume() {
        super.onResume()

        viewModel.fetchHabits()
        viewModel.fetchRecord()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        habitAdapter = HabitAdapter (
            onHabitClick = {
                val intent = Intent(requireActivity(), DetailHistoryActivity::class.java)
                intent.putExtra(HABIT_ID, it)
                startActivity(intent)
            }
        )
        binding.rvHabit.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = habitAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.bottom = 20
                }
            })
        }
    }

    private fun initEvent() {
        binding.ivTooltip.setOnClickListener {
            binding.tvTooltip.apply {
                visibility = if (isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
        binding.btnCreateHabit.setOnSingleClickListener {
            startActivity(Intent(requireActivity(), HabitCreateActivity::class.java))
        }
        binding.ivPrevMonth.setOnSingleClickListener {
            viewModel.onClickPrevMonth()
        }
        binding.ivNextMonth.setOnSingleClickListener {
            viewModel.onClickNextMonth()
        }
    }

    private fun fetchHabits(habits: List<HabitUI>) {
        habitAdapter.submitList(habits)
        binding.groupNoHabit.isVisible = habits.isEmpty()
        binding.ncvHasHabit.isVisible = habits.isNotEmpty()

        if(habits.isEmpty()) {
            binding.groupToolbar.visibility = View.INVISIBLE
            binding.tvTooltip.isVisible = false
        } else {
            binding.groupToolbar.visibility = View.VISIBLE
        }
    }

    private fun setThisMonthInfo(
        rewardCount: String,
        achievementCount: String,
        emoji: String,
        title: String,
        cardBackgroundResId: Int,
    ) {
        binding.tvThisMonthClap.text = rewardCount
        binding.tvThisMonthAchieveDays.text = achievementCount
        binding.clMostAchieve.setBackgroundResource(cardBackgroundResId)

        if(emoji.isEmpty()) {
            binding.tvMostAchieveHabitIcon.text = EmojiUtil.getEmojiString(EmojiUtil.Word.QUESTION)
            binding.tvMostAchieveHabitTitle.text = getString(R.string.no_achievement_habit_guide)
        } else {
            binding.tvMostAchieveHabitIcon.text = emoji
            binding.tvMostAchieveHabitTitle.text = title
        }
    }

    private fun setMonth(month: String) {
        binding.tvThisMonth.text = getString(R.string.this_month_history_title, month)
        binding.tvThisMonthClapTitle.text = getString(R.string.this_month_clap_title, month)
        binding.tvThisMonthAchieveDaysTitle.text = getString(R.string.this_month_achieve_days_title, month)
    }

    private fun setPreviousNextButtonState(
        previousMonthClickable: Boolean,
        nextMonthClickable: Boolean,
    ) {
        binding.ivPrevMonth.setImageResource(
            if(previousMonthClickable) {
                core_design.drawable.ic_left_arrow_default
            } else {
                core_design.drawable.ic_left_arrow_disable
            }
        )

        binding.ivNextMonth.setImageResource(
            if(nextMonthClickable) {
                core_design.drawable.ic_right_arrow_default
            } else {
                core_design.drawable.ic_right_arrow_disable
            }
        )
    }

    private fun setObserve() {
        viewModel.error
            .onEach { errorMessage -> ThreeDaysToast().error(requireContext(), errorMessage) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        if (it.isHabitInitialized && it.isRecordInitialized) {
                            binding.progressHistory.gone()
                            fetchHabits(it.habits)
                        }
                        else {
                            binding.ncvHasHabit.gone()
                            binding.groupToolbar.gone()
                            binding.progressHistory.visible()
                        }

                        setMonth(it.thisMonth.month.value.toString())
                        setThisMonthInfo(
                            rewardCount = it.rewardCount,
                            achievementCount = it.achievementCount,
                            emoji = it.emoji,
                            title = it.title,
                            cardBackgroundResId = it.cardBackgroundResId,
                        )
                        setPreviousNextButtonState(
                            previousMonthClickable = it.previousMonthClickable,
                            nextMonthClickable = it.nextMonthClickable,
                        )
                    }
                }
            }
        }
    }
}
