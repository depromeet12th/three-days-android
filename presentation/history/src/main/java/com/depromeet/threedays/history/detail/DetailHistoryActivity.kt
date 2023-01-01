package com.depromeet.threedays.history.detail

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.getDrawableCompat
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.key.HABIT_ID
import com.depromeet.threedays.history.R
import com.depromeet.threedays.history.databinding.ActivityDetailHistoryBinding
import com.depromeet.threedays.history.detail.view.DayBind
import com.depromeet.threedays.history.detail.view.Status
import com.depromeet.threedays.history.detail.view.getHabitColor
import com.depromeet.threedays.history.detail.view.getHabitLightColor
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields
import java.util.*
import com.depromeet.threedays.core_design_system.R as designR

@AndroidEntryPoint
class DetailHistoryActivity :
    BaseActivity<ActivityDetailHistoryBinding>(R.layout.activity_detail_history) {

    private val viewModel by viewModels<DetailHistoryViewModel>()

    private val immutableCurrentMonth = YearMonth.now()
    private lateinit var firstMonth: YearMonth

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
        viewModel.getHabitAchievementDateList(habitId)
    }

    private fun initView() {
        binding.ivPrevious.setOnSingleClickListener {
            binding.cvHistory.findFirstVisibleMonth()?.let {
                binding.cvHistory.smoothScrollToMonth(it.yearMonth.previousMonth)
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    it.yearMonth.previousMonth.year,
                    it.yearMonth.previousMonth.monthValue
                )
                viewModel.setCurrentCalendarDate(it.yearMonth.previousMonth.atDay(viewModel.state.value.today.dayOfMonth))
                viewModel.getHabitAchievementDateList(viewModel.habitId)

                binding.ivPrevious.isEnabled = (firstMonth != it.yearMonth.previousMonth)
                binding.ivNext.isEnabled = (immutableCurrentMonth != it.yearMonth.previousMonth)
            }
        }

        binding.ivNext.isEnabled = false
        binding.ivNext.setOnSingleClickListener {
            binding.cvHistory.findFirstVisibleMonth()?.let {
                binding.cvHistory.smoothScrollToMonth(it.yearMonth.nextMonth)
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    it.yearMonth.nextMonth.year,
                    it.yearMonth.nextMonth.monthValue
                )

                viewModel.setCurrentCalendarDate(it.yearMonth.nextMonth.atDay(viewModel.state.value.today.dayOfMonth))
                viewModel.getHabitAchievementDateList(viewModel.habitId)

                binding.ivPrevious.isEnabled = (firstMonth != it.yearMonth.nextMonth)
                binding.ivNext.isEnabled = (immutableCurrentMonth != it.yearMonth.nextMonth)
            }
        }

        binding.tvToday.text = String.format(
            "%d년 %02d월",
            immutableCurrentMonth.year,
            immutableCurrentMonth.monthValue
        )

        binding.ivBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun observe() {
        viewModel.state
            .onEach { state ->
                val (isInitialized, isAchievementInitialized, isOnlyListChanged, habit, currentMonthStatic, achievementDateWithStatusList, currentCalendarDate) = state
                if(isInitialized) {
                    if(isAchievementInitialized) {
                        binding.tvClapCountOfMonth.text = String.format(getString(R.string.month_clap_count), currentCalendarDate.monthValue, currentMonthStatic.claps)
                        binding.tvAchievementDayCountOfMonth.text = String.format(getString(R.string.month_achievement_day_count), currentCalendarDate.monthValue, currentMonthStatic.achievements)

                        if(!isOnlyListChanged) {
                            firstMonth = immutableCurrentMonth.minusMonths(
                                ChronoUnit.MONTHS.between(
                                    habit.createAt,
                                    state.today
                                )
                            ).also {
                                binding.ivPrevious.isEnabled = (immutableCurrentMonth != it)
                            }

                            initCalendar(
                                color = habit.color,
                                dateList = achievementDateWithStatusList,
                                firstMonth = firstMonth,
                                lastMonth = immutableCurrentMonth.plusMonths(0)
                            )
                            viewModel.setIsOnlyListChanged(true)
                        }
                        else {
                            binding.cvHistory.dayBinder = DayBind.newInstance(achievementDateWithStatusList, habit.color)
                        }
                    }
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

    private fun initCalendar(color: Color, dateList: Map<LocalDate, Status>, firstMonth: YearMonth, lastMonth: YearMonth) {
        val firstDayOfWeek = WeekFields.of(Locale.KOREAN).firstDayOfWeek

        with(binding) {
            cvHistory.dayBinder = DayBind.newInstance(dateList, color)
            cvHistory.setup(firstMonth, lastMonth, firstDayOfWeek)
            cvHistory.scrollToMonth(immutableCurrentMonth)
            cvHistory.monthScrollListener = { calendar ->
                binding.tvToday.text = String.format(
                    "%d년 %02d월",
                    calendar.yearMonth.year,
                    calendar.yearMonth.monthValue
                )
            }
        }
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
