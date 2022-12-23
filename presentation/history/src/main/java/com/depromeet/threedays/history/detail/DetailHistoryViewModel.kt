package com.depromeet.threedays.history.detail

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.repository.AchievementRepository
import com.depromeet.threedays.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DetailHistoryViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val achievementRepository: AchievementRepository
) : BaseViewModel() {
    private var _habitId: Long = 0
    val habitId: Long
        get() = _habitId

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    fun getHabit(habitId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                habitRepository.getHabit(habitId = habitId)
            }.onSuccess { habit ->
                _state.value = _state.value.copy(
                    isInitialized = true,
                    habit = habit
                )
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun getHabitAchievementDateList(habitId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                achievementRepository.getHabitAchievements(
                    habitId = habitId,
                    from = state.value.fromDate,
                    to = state.value.toDate,
                ).map { achievement ->
                    achievement.achievementDate
                }
            }.onSuccess { habitAchievementDateList ->
                _state.value = _state.value.copy(
                    isAchievementInitialized = true,
                    achievementDateList = habitAchievementDateList
                )
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun setHabitId(habitId: Long) {
        _habitId = habitId
    }

    fun setCurrentCalendarDate(newCurrentCalendarDate: LocalDate) {
        _state.value = _state.value.copy(
            currentCalendarDate = newCurrentCalendarDate
        )
    }

    fun setIsOnlyListChanged(isOnlyListChanged: Boolean) {
        _state.value = _state.value.copy(
            isOnlyListChanged = isOnlyListChanged
        )
    }

    data class State(
        val isInitialized: Boolean = false,
        val isAchievementInitialized: Boolean = false,
        val isOnlyListChanged: Boolean = false,
        val habit: SingleHabit = SingleHabit.EMPTY,
        val achievementDateList: List<LocalDate> = emptyList(),
        val today: LocalDateTime = LocalDateTime.now(),
        val currentCalendarDate: LocalDate = LocalDate.now(),
    ) {
        val daysAfterCreate: Int
            get() {
                return ChronoUnit.DAYS.between(habit.createAt, today).toInt()
            }

        val dayOfWeekText: String
            get() {
                return if(habit.dayOfWeeks.size == 7) "매일"
                else if (habit.dayOfWeeks.size == 5 && habit.dayOfWeeks.containsAll(listOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))) {
                    "평일"
                }
                else {
                    val formattedDayOfWeekList = habit.dayOfWeeks.map { dayOfWeek ->
                        when (dayOfWeek) {
                            DayOfWeek.MONDAY -> "월"
                            DayOfWeek.TUESDAY -> "화"
                            DayOfWeek.WEDNESDAY -> "수"
                            DayOfWeek.THURSDAY -> "목"
                            DayOfWeek.FRIDAY -> "금"
                            DayOfWeek.SATURDAY -> "토"
                            DayOfWeek.SUNDAY -> "일"
                        }
                    }
                    return formattedDayOfWeekList.joinToString(", " )
                }
            }

        val formattedCreatedDay: String
            get() {
                val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
                return habit.createAt.format(formatter)
            }

        val fromDate: LocalDate
            get() {
                val date = currentCalendarDate
                return date.minusMonths(1)
            }

        val toDate: LocalDate
            get() {
                val date = currentCalendarDate
                return if (currentCalendarDate.year == today.year && currentCalendarDate.monthValue == today.monthValue) {
                    date
                } else
                    date.plusMonths(1)
            }
    }
}
