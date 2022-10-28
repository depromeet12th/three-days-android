package com.depromeet.threedays.register.add

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.repository.GoalRepository
import com.depromeet.threedays.register.SimpleGoal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class GoalAddViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _goal = MutableStateFlow(SimpleGoal.EMPTY)
    val goal: StateFlow<SimpleGoal>
        get() = _goal.asStateFlow()

    fun onSaveGoalClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                val goal = with(_goal.value) {
                    SaveGoalRequest(
                        title = title.value,
                        startDate = startDate,
                        endDate = endDate,
                        startTime = startTime,
                        notificationTime = notificationTime,
                        notificationContent = notificationContent
                    )
                }
                goalRepository.create(goal)
            }.onSuccess {
                _action.emit(Action.SaveClick)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun onStartCalendarClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.StartCalendarClick(getRealDate(today, goal.value.startDate ?: today)))
        }
    }

    fun onEndCalendarClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.EndCalendarClick(getRealDate(today, goal.value.endDate ?: today)))
        }
    }

    fun onRunTimeClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.RunTimeClick(getRealDate(today, goal.value.startTime ?: today)))
        }
    }

    fun onNotificationTimeClick() {
        viewModelScope.launch {
            val today = ZonedDateTime.now(ZoneId.systemDefault())
            _action.emit(Action.NotificationTimeClick(getRealDate(today, goal.value.notificationTime ?: today)))
        }
    }

    private fun getRealDate(today: ZonedDateTime, currentDate: ZonedDateTime): ZonedDateTime {
        return if (today == currentDate) today else currentDate
    }

    fun setStartDate(newYear: Int, newMonth: Int, newDay: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                startDate = (_goal.value.startDate ?: ZonedDateTime.now(ZoneId.systemDefault()))
                    .withYear(newYear)
                    .withMonth(newMonth)
                    .withDayOfMonth(newDay)
            )
        }

    }

    fun setEndDate(newYear: Int, newMonth: Int, newDay: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                endDate = (_goal.value.endDate ?: ZonedDateTime.now(ZoneId.systemDefault()))
                    .withYear(newYear)
                    .withMonth(newMonth)
                    .withDayOfMonth(newDay)
            )
        }
    }

    fun setStartTime(newHour: Int, newMin: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                startTime = (_goal.value.startTime ?: ZonedDateTime.now(ZoneId.systemDefault()))
                    .withHour(newHour)
                    .withMinute(newMin)
            )
        }
    }

    fun setNotificationTime(newHour: Int, newMin: Int) {
        viewModelScope.launch {
            _goal.value = _goal.value.copy(
                notificationTime = (_goal.value.notificationTime ?: ZonedDateTime.now(ZoneId.systemDefault()))
                    .withHour(newHour)
                    .withMinute(newMin)
            )
        }
    }

    sealed class Action {
        data class StartCalendarClick(val currentDate: ZonedDateTime) : Action()
        data class EndCalendarClick(val currentDate: ZonedDateTime) : Action()
        data class RunTimeClick(val currentTime: ZonedDateTime) : Action()
        data class NotificationTimeClick(val currentTime: ZonedDateTime) : Action()
        object SaveClick : Action()
    }
}
