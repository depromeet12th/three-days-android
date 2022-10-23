package com.depromeet.threedays.register.update

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.repository.GoalRepository
import com.depromeet.threedays.register.SimpleGoal
import com.depromeet.threedays.register.toSimpleGoal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class GoalUpdateViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _goal = MutableStateFlow(SimpleGoal.EMPTY)
    val goal: StateFlow<SimpleGoal>
        get() = _goal.asStateFlow()

    fun getGoalById(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                goalRepository.findById(id)
            }.onSuccess {
                _goal.value = it.toSimpleGoal()
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun onUpdateGoalClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                goalRepository.update(
                    goalId = goal.value.goalId,
                    title = goal.value.title.value
                )
            }.onSuccess {
                _action.emit(Action.UpdateClick)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

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
        object UpdateClick : Action()
    }
}
