package com.depromeet.threedays.create.create

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitCreateViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : BaseViewModel() {
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val initTime = LocalTime.now()

    val title = MutableStateFlow(String.Empty)
    val emoji = MutableStateFlow(String.Empty)
    private val dayOfWeekList = MutableStateFlow(emptyList<DayOfWeek>())
    val notificationContent = MutableStateFlow(String.Empty)
    private val notificationTime = MutableStateFlow(initTime)
    val color = MutableStateFlow(Color.GREEN)

    private var _isSaveHabitEnable = MutableStateFlow(false)
    val isSaveHabitEnable: StateFlow<Boolean>
        get() = _isSaveHabitEnable

    fun setEmoji(emoji: String) {
        this.emoji.value = emoji
    }

    fun setColor(color: Color) {
        this.color.value = color
    }

    fun setNotificationTime(time: LocalTime) {
        notificationTime.value = time
    }

    fun setSaveHabitEnable() {
        viewModelScope.launch {
            combine(title, dayOfWeekList) { title, dayOfWeekList ->
                title.isNotEmpty() && dayOfWeekList.size >= MIN_DAY_OF_WEEK_NUM
            }.collect {
                _isSaveHabitEnable.value = it
            }
        }
    }

    fun onSaveGoalClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                val habit = CreateHabit(
                    title = title.value,
                    emoji = emoji.value,
                    dayOfWeeks = dayOfWeekList.value,
                    notification = CreateHabit.Notification(
                        contents = notificationContent.value,
                        notificationTime = notificationTime.value,
                    ),
                    color = color.value
                )
                habitRepository.createHabit(habit)
            }.onSuccess {
                _action.emit(Action.SaveClick)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun onNotificationTimeClick() {
        viewModelScope.launch {
            val time = notificationTime.value
            _action.emit(Action.NotificationTimeClick(time))
        }
    }

    fun addSavingDayOfWeek(dayOfWeek: DayOfWeek) {
        val newList: MutableList<DayOfWeek> = dayOfWeekList.value.toMutableList()
        newList.add(dayOfWeek)
        dayOfWeekList.value = newList.toList()
    }

    fun deleteSavingDayOfWeek(dayOfWeek: DayOfWeek) {
        val newList: MutableList<DayOfWeek> = dayOfWeekList.value.toMutableList()
        newList.remove(dayOfWeek)
        dayOfWeekList.value = newList.toList()
    }

    sealed class Action {
        data class NotificationTimeClick(val currentTime: LocalTime) : Action()
        object SaveClick : Action()
    }

    companion object {
        const val MIN_DAY_OF_WEEK_NUM = 3
    }
}
