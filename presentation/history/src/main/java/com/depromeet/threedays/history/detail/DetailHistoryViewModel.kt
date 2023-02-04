package com.depromeet.threedays.history.detail

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.AchievementRepository
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.history.detail.view.Status
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
            habitRepository.getHabit(habitId = habitId)
                .onSuccess { habit ->
                    _state.value = _state.value.copy(
                        isInitialized = true,
                        habit = habit
                    )
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    sendErrorMessage(throwable.message)
                }
        }
    }

    fun getHabitAchievementDateList(habitId: Long) {
        viewModelScope.launch {
            achievementRepository.getHabitAchievements(
                habitId = habitId,
                from = state.value.fromDate,
                to = state.value.toDate,
            ).onSuccess { achievement ->
                val sortedHabitAchievementDateList =
                    achievement.map { it.achievementDate }.sorted()

                _state.value = _state.value.copy(
                    isAchievementInitialized = true,
                    achievementDateList = sortedHabitAchievementDateList,
                    achievementDateWithStatusList = getAchievementDateWithStatusList(
                        sortedHabitAchievementDateList
                    ),
                    currentMonthStatic = MonthStatic(
                        achievements = achievement.size,
                        claps = achievement.filter { it.sequence == 3 }.size
                    )
                )
            }.onFailure { throwable ->
                throwable as ThreeDaysException
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

    private fun getAchievementDateWithStatusList(achievementDateList: List<LocalDate>): MutableMap<LocalDate, Status> {
        val map = mutableMapOf<LocalDate, Status>()
        var status = Status.SINGLE

        for (index in achievementDateList.indices) {
//            if (index + 1 < achievementDateList.size) {
//                val current = achievementDateList[index]
//                val tomorrow = achievementDateList[index + 1]
//                when (status) {
//                    Status.SINGLE, Status.END -> {
//                        if (index + 2 < achievementDateList.size) {
//                            val afterTomorrow = achievementDateList[index + 2]
//                            if (current.plusDays(1) == tomorrow && current.plusDays(2) == afterTomorrow) {
//                                status = Status.START
//                                map[achievementDateList[index]] = status
//                                continue
//                            }
//                        }
//                        status = Status.SINGLE
//                    }
//                    Status.START -> {
//                        status = Status.BETWEEN
//                    }
//                    Status.BETWEEN -> {
//                        status = Status.END
//                    }
//                }
//
//                map[achievementDateList[index]] = status
//            } else {
//                when (status) {
//                    Status.START, Status.SINGLE, Status.END -> {
//                        map[achievementDateList[index]] = Status.SINGLE
//                    }
//                    Status.BETWEEN -> {
//                        map[achievementDateList[index]] = Status.END
//                    }
//                }
//            }
            /**
             * 위의 검증 로직은 추후 업데이트 버전에서 재사용 될 가능성이 있어 삭제하지 않고 유지합니다
             * 다만 연속이 아닌 하나의 동그라미로만 표시돼야 하기 때문에 Status.SINGLE 로 모든 상태를 재할당합니다 */
            map[achievementDateList[index]] = Status.SINGLE
        }

        return map
    }

    data class State(
        val isInitialized: Boolean = false,
        val isAchievementInitialized: Boolean = false,
        val isOnlyListChanged: Boolean = false,
        val habit: SingleHabit = SingleHabit.EMPTY,
        val currentMonthStatic: MonthStatic = MonthStatic.EMPTY,
        val achievementDateWithStatusList: Map<LocalDate, Status> = emptyMap(),
        val currentCalendarDate: LocalDate = LocalDate.now(),
        val today: LocalDateTime = LocalDateTime.now(),
        val achievementDateList: List<LocalDate> = emptyList(),
    ) {
        val daysAfterCreate: Int
            get() {
                return (ChronoUnit.DAYS.between(habit.createAt, today).toInt() + 1)
            }

        val dayOfWeekText: String
            get() {
                return if (habit.dayOfWeeks.size == 7) "매일"
                else if (habit.dayOfWeeks.size == 5 && habit.dayOfWeeks.containsAll(
                        listOf(
                            DayOfWeek.MONDAY,
                            DayOfWeek.THURSDAY,
                            DayOfWeek.WEDNESDAY,
                            DayOfWeek.THURSDAY,
                            DayOfWeek.FRIDAY
                        )
                    )
                ) {
                    "평일"
                } else {
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
                    return formattedDayOfWeekList.joinToString(", ")
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
                return date.withDayOfMonth(1)
            }

        val toDate: LocalDate
            get() {
                val date = currentCalendarDate
                return date.withDayOfMonth(date.lengthOfMonth())
            }
    }

    data class MonthStatic(
        val claps: Int,
        val achievements: Int
    ) {
        companion object {
            val EMPTY = MonthStatic(
                claps = 0,
                achievements = 0
            )
        }
    }
}
