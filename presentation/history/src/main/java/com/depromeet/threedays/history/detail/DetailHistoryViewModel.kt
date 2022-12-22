package com.depromeet.threedays.history.detail

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DetailHistoryViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : BaseViewModel() {
    private var habitId: Long = 0

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

    fun setHabitId(habitId: Long) {
        this.habitId = habitId
    }

    data class State(
        val isInitialized: Boolean = false,
        val today: LocalDateTime = LocalDateTime.now(),
        val habit: SingleHabit = SingleHabit.EMPTY
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
    }
}
