package com.depromeet.threedays.create.create

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.create.CreateNotification
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.util.EmojiUtil
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
    private val initTime = LocalTime.now()
    private val initEmoji = Emoji.from(EmojiUtil.Word.SMILE)
    private val initColor = Color.GREEN

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    val title = MutableStateFlow(String.Empty)

    private val _emoji = MutableStateFlow(initEmoji)
    val emoji: StateFlow<Emoji>
        get() = _emoji.asStateFlow()

    private val dayOfWeekList = MutableStateFlow(emptyList<DayOfWeek>())

    private val _color = MutableStateFlow(initColor)
    val color: StateFlow<Color>
        get() = _color.asStateFlow()

    private val notification = MutableStateFlow(CreateNotification(
        initNotificationTime = false,
        notificationInfoActive = true,
        notificationTime = initTime,
        notificationContent = String.Empty)
    )

    private val _isInformationEntered = MutableStateFlow(false)
    val isInformationEntered: StateFlow<Boolean>
        get() = _isInformationEntered.asStateFlow()

    private var _isSaveHabitEnable = MutableStateFlow(false)
    val isSaveHabitEnable: StateFlow<Boolean>
        get() = _isSaveHabitEnable


    fun setEmoji(emoji: Emoji) {
        _emoji.value = emoji
    }

    fun setColor(color: Color) {
        _color.value = color
    }

    fun setNotificationTime(time: LocalTime) {
        notification.value = notification.value.copy(
            initNotificationTime = true,
            notificationTime = time
        )
    }

    fun setNotificationInfoActive(isActive: Boolean) {
        notification.value = notification.value.copy(
            notificationInfoActive = isActive
        )
    }

    fun setNotificationContent(text: String) {
        notification.value = notification.value.copy(
            notificationContent = text
        )
    }

    fun setSaveHabitEnable() {
        viewModelScope.launch {
            combine(title, dayOfWeekList, notification) { title, dayOfWeekList, notification->
                val notificationEnable =
                    if(notification.notificationInfoActive) {
                        notification.notificationContent.isNotEmpty() && notification.initNotificationTime
                    }
                    else true
                title.isNotEmpty() && dayOfWeekList.size >= MIN_DAY_OF_WEEK_NUM && notificationEnable
            }.collect {
                _isSaveHabitEnable.value = true
            }
        }
    }

    fun setInformationEntered() {
        viewModelScope.launch {
            combine(title, emoji, dayOfWeekList, notification, color) { title, emoji, dayOfWeekList, notification, color ->
                val isChangedNotificationToNull =
                    if (notification.notificationContent.isNotEmpty() || notification.initNotificationTime) {
                        !notification.notificationInfoActive
                    }
                    else false
                title.isNotEmpty()|| dayOfWeekList.isNotEmpty() || emoji != initEmoji || notification.notificationContent.isNotEmpty() || notification.initNotificationTime || color != initColor || isChangedNotificationToNull
            }.collect {
                _isInformationEntered.value = it
            }
        }
    }

    fun onCreateHabitClick() {
        viewModelScope.launch {
            val notification = if (notification.value.notificationInfoActive) {
                CreateHabit.Notification (
                    contents = notification.value.notificationContent,
                    notificationTime = notification.value.notificationTime,
                )
            } else null

            val habit = CreateHabit(
                title = title.value,
                emoji = emoji.value,
                dayOfWeeks = dayOfWeekList.value,
                notification = notification,
                color = color.value
            )
            habitRepository.createHabit(habit)
                .onSuccess {
                    _action.emit(Action.SaveClick)
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    sendErrorMessage(throwable.message)
                }
        }
    }

    fun onNotificationTimeClick() {
        viewModelScope.launch {
            val time = notification.value.notificationTime
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
