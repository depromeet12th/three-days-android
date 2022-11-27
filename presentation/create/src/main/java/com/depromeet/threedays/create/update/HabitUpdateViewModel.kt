package com.depromeet.threedays.create.update

import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.create.SimpleGoal
import com.depromeet.threedays.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HabitUpdateViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _goal = MutableStateFlow(SimpleGoal.EMPTY)
    val goal: StateFlow<SimpleGoal>
        get() = _goal.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean>
        get() = _isInitialized

//    fun getGoalById(id: Long) {
//        viewModelScope.launch {
//            kotlin.runCatching {
//               // habitRepository.findById(id)
//            }.onSuccess {
//                //_goal.value = it.toSimpleGoal()
//                _isInitialized.value = true
//            }.onFailure { throwable ->
//                sendErrorMessage(throwable.message)
//            }
//        }
//    }
//
//    fun onUpdateGoalClick() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val newGoal = with(goal.value) {
//                    UpdateGoalRequest(
//                        goalId = goalId,
//                        title = title.value,
//                        startDate = startDate,
//                        endDate = endDate,
//                        startTime = startTime,
//                        notificationTime = notificationTime,
//                        notificationContent = notificationContent,
//                        sequence = sequence,
//                        lastAchievementDate = lastAchievementDate,
//                        clapIndex = clapIndex,
//                        clapChecked = clapChecked
//                    )
//                }
//                //habitRepository.update(newGoal)
//            }.onSuccess {
//                _action.emit(Action.UpdateClick)
//            }.onFailure { throwable ->
//                sendErrorMessage(throwable.message)
//            }
//        }
//    }

    sealed class Action {
        data class NotificationTimeClick(val currentTime: ZonedDateTime) : Action()
        object UpdateClick : Action()
    }
}
