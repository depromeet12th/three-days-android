package com.depromeet.threedays.history.detail

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.getDrawableCompat
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.key.HABIT_ID
import com.depromeet.threedays.history.R
import com.depromeet.threedays.history.databinding.ActivityDetailHistoryBinding
import com.depromeet.threedays.history.detail.view.*
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import com.depromeet.threedays.core_design_system.R as designR

@AndroidEntryPoint
class DetailHistoryActivity :
    BaseActivity<ActivityDetailHistoryBinding>(R.layout.activity_detail_history) {

    private val viewModel by viewModels<DetailHistoryViewModel>()

    private val list = listOf(
        "2022-07-04", "2022-07-05", "2022-07-06", "2022-07-07", "2022-07-14", "2022-07-26",
        "2022-06-03", "2022-06-17", "2022-06-18", "2022-06-19", "2022-06-20", "2022-06-21",
        "2022-06-23", "2022-06-25", "2022-06-26", "2022-08-05", "2022-09-20", "2022-10-02",
        "2022-10-10", "2022-10-11", "2022-10-14", "2022-10-15", "2022-10-30", "2022-10-31",
        "2022-11-01", "2022-11-07", "2022-11-09", "2022-11-10", "2022-11-11", "2022-11-12",
        "2022-11-14", "2022-11-15", "2022-11-16", "2022-11-17", "2022-11-18", "2022-11-20",
        "2022-11-24", "2022-11-25", "2022-12-01", "2022-12-07", "2022-12-09", "2022-12-10",
        "2022-12-11", "2022-12-12", "2022-12-14", "2022-12-15", "2022-12-16", "2022-12-17",
        "2022-12-20", "2022-12-24", "2022-12-25"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initView()
        observe()
    }

    private fun initData() {
        val habitId = intent.getLongExtra(HABIT_ID, 1)
        viewModel.setHabitId(habitId)
        viewModel.getHabit(habitId)
    }

    private fun initView() {
        binding.ivPrevious.setOnClickListener {
            binding.cvHistory.findFirstVisibleMonth()?.let {
                binding.cvHistory.smoothScrollToMonth(it.yearMonth.previousMonth)
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    it.yearMonth.previousMonth.year,
                    it.yearMonth.previousMonth.monthValue
                )

                binding.ivNext.isEnabled = (currentMonth != it.yearMonth.previousMonth)
            }
        }

        binding.ivNext.isEnabled = false
        binding.ivNext.setOnClickListener {
            binding.cvHistory.findFirstVisibleMonth()?.let {
                binding.cvHistory.smoothScrollToMonth(it.yearMonth.nextMonth)
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    it.yearMonth.nextMonth.year,
                    it.yearMonth.nextMonth.monthValue
                )

                binding.ivNext.isEnabled = (currentMonth != it.yearMonth.nextMonth)
            }
        }

        binding.tvToday.text = String.format(
            "%d년 %02d월",
            currentMonth.year,
            currentMonth.monthValue
        )

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun observe() {
        viewModel.state
            .onEach { state ->
                val (isInitialized, _, habit) = state
                if(isInitialized) {
                    initCalendar(habit.color)
                    setColorView(habit.color)

                    binding.tvTitle.text = habit.title
                    binding.tvEmoji.text = habit.emoji.value
                    binding.tvCreateDays.text = String.format("%d일째", state.daysAfterCreate)
                    binding.tvDayOfWeek.text = state.dayOfWeekText
                    binding.tvStartDate.text = String.format("시작일 : %s", state.formattedCreatedDay)
                    binding.tvClapCount.text = habit.reward.toString()
                    binding.tvExecuteDayCount.text = habit.totalAchievementCount.toString()
                }
            }.launchIn(lifecycleScope)
    }

    private fun initCalendar(color: Color) {
        val executeDateWithStatusList = getExecuteDateWithStatusList(list).toMap()
        with(binding) {
            cvHistory.dayBinder = DayBind.newInstance(executeDateWithStatusList, color)
            cvHistory.setup(firstMonth, lastMonth, firstDayOfWeek)
            cvHistory.scrollToMonth(currentMonth)
            cvHistory.monthScrollListener = { calendar ->
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    calendar.yearMonth.year,
                    calendar.yearMonth.monthValue
                )
                binding.ivNext.isEnabled = (currentMonth != calendar.yearMonth)
            }
        }
    }

    private fun getExecuteDateWithStatusList(dateList: List<String>): MutableMap<String, Status> {
        val periodList = mutableMapOf<String, Status>()
        var status = Status.SINGLE
        for (index in dateList.indices) {
            if (index + 1 < dateList.size) {
                val current = LocalDate.parse(dateList[index])
                val tomorrow = LocalDate.parse(dateList[index + 1])
                when (status) {
                    Status.SINGLE, Status.END -> {
                        if(index + 2 < dateList.size) {
                            val afterTomorrow = LocalDate.parse(dateList[index + 2])
                            if (current.plusDays(1) == tomorrow && current.plusDays(2) == afterTomorrow) {
                                status = Status.START
                                periodList[dateList[index]] = status
                                continue
                            }
                        }
                        status = Status.SINGLE
                    }
                    Status.START -> {
                        status = Status.BETWEEN
                    }
                    Status.BETWEEN -> {
                        status = Status.END
                    }
                }

                periodList[dateList[index]] = status
            } else {
                when (status) {
                    Status.START, Status.SINGLE, Status.END -> {
                        periodList[dateList[index]] = Status.SINGLE
                    }
                    Status.BETWEEN -> {
                        periodList[dateList[index]] = Status.END
                    }
                }
            }
        }

        return periodList
    }

    private fun setColorView(color: Color) {
        val habitColor = this.getHabitColor(color)
        val habitLightColor = this.getHabitLightColor(color)
        val createDaysDrawable = this.getDrawableCompat(designR.drawable.bg_rect_green10_r5) as GradientDrawable
        val rangeDrawable = this.getDrawableCompat(designR.drawable.bg_rect_green50_r4) as GradientDrawable
        val singleDrawable = this.getDrawableCompat(R.drawable.bg_single_selection) as GradientDrawable

        createDaysDrawable.setColor(habitLightColor)
        rangeDrawable.setColor(habitColor)
        singleDrawable.setColor(habitColor)

        binding.tvCreateDays.run {
            background = createDaysDrawable
            binding.tvCreateDays.setTextColor(habitColor)
        }
        binding.vRange.background = rangeDrawable
        binding.vSingle.background = singleDrawable
    }
}
