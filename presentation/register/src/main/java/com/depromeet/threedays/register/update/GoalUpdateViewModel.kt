package com.depromeet.threedays.register.update

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

class GoalUpdateViewModel : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _goal = MutableStateFlow(
        SimpleGoal(
            title = MutableStateFlow(""),
            startDate = ZonedDateTime.now(ZoneId.systemDefault()),
            endDate = ZonedDateTime.now(ZoneId.systemDefault()),
            startTime = ZonedDateTime.now(ZoneId.systemDefault()),
            notificationTime = ZonedDateTime.now(ZoneId.systemDefault()),
            notificationContent = ""
        )
    )
    val goal: StateFlow<SimpleGoal>
        get() = _goal.asStateFlow()

    fun onStartCalendarClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.StartCalendarClick(getRealDate(today, goal.value.startDate)))
        }
    }

    fun onEndCalendarClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.EndCalendarClick(getRealDate(today, goal.value.endDate)))
        }
    }

    fun onRunTimeClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.RunTimeClick(getRealDate(today, goal.value.startTime)))
        }
    }

    private fun getRealDate(today: ZonedDateTime, currentDate: ZonedDateTime): ZonedDateTime {
        return if (today == currentDate) today else currentDate
    }

    fun setStartDate(newYear: Int, newMonth: Int, newDay: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                startDate = _goal.value.startDate
                    .withYear(newYear)
                    .withMonth(newMonth)
                    .withDayOfMonth(newDay)
            )
        }

    }

    fun setEndDate(newYear: Int, newMonth: Int, newDay: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                endDate = _goal.value.endDate
                    .withYear(newYear)
                    .withMonth(newMonth)
                    .withDayOfMonth(newDay)
            )
        }
    }

    fun setStartTimeWithNotificationTime(newHour: Int, newMin: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                startTime = _goal.value.startTime
                    .withHour(newHour)
                    .withMinute(newMin),
                notificationTime = _goal.value.notificationTime
                    .withHour(newHour)
                    .withMinute(newMin)
            )
        }
    }

    sealed class Action {
        data class StartCalendarClick(val currentDate: ZonedDateTime) : Action()
        data class EndCalendarClick(val currentDate: ZonedDateTime) : Action()
        data class RunTimeClick(val currentTime: ZonedDateTime) : Action()
    }

    data class SimpleGoal(
        val title: MutableStateFlow<String>,
        var startDate: ZonedDateTime,
        var endDate: ZonedDateTime,
        var startTime: ZonedDateTime,
        var notificationTime: ZonedDateTime,
        var notificationContent: String
    )
}
