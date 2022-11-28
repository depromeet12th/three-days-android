package com.depromeet.threedays.create.create

import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.create.SimpleGoal
import com.depromeet.threedays.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HabitCreateViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _goal = MutableStateFlow(SimpleGoal.EMPTY)
    val goal: StateFlow<SimpleGoal>
        get() = _goal.asStateFlow()

//    fun onSaveGoalClick() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val goal = with(_goal.value) {
//                    SaveGoalRequest(
//                        title = title.value,
//                        startDate = startDate,
//                        endDate = endDate,
//                        startTime = startTime,
//                        notificationTime = notificationTime,
//                        notificationContent = notificationContent
//                    )
//                }
//                //habitRepository.create(goal)
//            }.onSuccess {
//                _action.emit(Action.SaveClick)
//            }.onFailure { throwable ->
//                sendErrorMessage(throwable.message)
//            }
//        }
//    }

    sealed class Action {
        data class NotificationTimeClick(val currentTime: ZonedDateTime) : Action()
        object SaveClick : Action()
    }
}
