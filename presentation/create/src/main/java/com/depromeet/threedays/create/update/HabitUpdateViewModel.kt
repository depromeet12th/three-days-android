package com.depromeet.threedays.create.update

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.util.EmojiUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitUpdateViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : BaseViewModel() {
    private val initTime = LocalTime.now()
    private val initEmoji = Emoji.from(EmojiUtil.Word.SMILE)
    private val initColor = Color.GREEN
    private var habitId: Long = 0

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    val title = MutableStateFlow(String.Empty)

    private val _emoji = MutableStateFlow(initEmoji)
    val emoji: StateFlow<Emoji>
        get() = _emoji.asStateFlow()

    private val _dayOfWeekList = MutableStateFlow(emptyList<DayOfWeek>())
    val dayOfWeekList: StateFlow<List<DayOfWeek>>
        get() = _dayOfWeekList.asStateFlow()

    private val _color = MutableStateFlow(initColor)
    val color: StateFlow<Color>
        get() = _color.asStateFlow()

    private val _notification = MutableStateFlow(Notification(
        initNotificationTime = false,
        notificationTime = initTime,
        notificationContent = String.Empty
    ))
    val notification: StateFlow<Notification>
        get() = _notification.asStateFlow()

    private val isNotificationInfoActive = MutableStateFlow(true)

    private val _isInformationEntered = MutableStateFlow(false)
    val isInformationEntered: StateFlow<Boolean>
        get() = _isInformationEntered.asStateFlow()

    private var _isSaveHabitEnable = MutableStateFlow(false)
    val isSaveHabitEnable: StateFlow<Boolean>
        get() = _isSaveHabitEnable

//    fun getHabit(habitId: Long) {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                habitRepository.getHabit(habitId = habitId)
//            }.onSuccess { habit ->
//                title.value = habit.title
//                _emoji.value = habit.emoji
//                _dayOfWeekList.value = habit.dayOfWeeks
//                _color.value = habit.color
//                _notification.value = _notification.value.copy(
//                    initNotificationTime = habit.notification != null,
//                    notificationTime = habit.notification?.notificationTime ?: LocalTime.now(),
//                    notificationContent = habit.notification?.contents ?: String.Empty
//                )
//            }.onFailure { throwable ->
//                sendErrorMessage(throwable.message)
//            }
//        }
//    }

    fun setHabitId(habitId: Long) {
        this.habitId = habitId
    }

    fun setEmoji(emoji: Emoji) {
        _emoji.value = emoji
    }

    fun setColor(color: Color) {
        _color.value = color
    }

    fun setNotificationTime(time: LocalTime) {
        _notification.value = _notification.value.copy(
            initNotificationTime = true,
            notificationTime = time
        )
    }

    fun setNotificationInfoActive(isActive: Boolean) {
        isNotificationInfoActive.value = isActive
    }

    fun setNotificationContent(text: String) {
        _notification.value = _notification.value.copy(
            notificationContent = text
        )
    }

    fun setSaveHabitEnable() {
        viewModelScope.launch {
            combine(title, dayOfWeekList, notification, isNotificationInfoActive) { title, dayOfWeekList, notification, isNotificationInfoActive ->
                val notificationEnable =
                    if(isNotificationInfoActive) { notification.notificationContent.isNotEmpty() && notification.initNotificationTime }
                    else true
                title.isNotEmpty() && dayOfWeekList.size >= MIN_DAY_OF_WEEK_NUM && notificationEnable
            }.collect {
                _isSaveHabitEnable.value = it
            }
        }
    }

    fun setInformationEntered() {
        viewModelScope.launch {
            combine(title, emoji, dayOfWeekList, notification, color) { title, emoji, dayOfWeekList, notification, color ->
                title.isNotEmpty()|| dayOfWeekList.isNotEmpty() || emoji != initEmoji || notification.notificationContent.isNotEmpty() || notification.initNotificationTime || color != initColor
            }.collect {
                _isInformationEntered.value = it
            }
        }
    }

    fun onSaveGoalClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                val notification = if (isNotificationInfoActive.value) {
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
                habitRepository.updateHabit(habitId = habitId, habit = habit)
            }.onSuccess {
                _action.emit(Action.SaveClick)
            }.onFailure { throwable ->
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
        _dayOfWeekList.value = newList.toList()
    }

    fun deleteSavingDayOfWeek(dayOfWeek: DayOfWeek) {
        val newList: MutableList<DayOfWeek> = dayOfWeekList.value.toMutableList()
        newList.remove(dayOfWeek)
        _dayOfWeekList.value = newList.toList()
    }

    sealed class Action {
        data class NotificationTimeClick(val currentTime: LocalTime) : Action()
        object SaveClick : Action()
    }

    data class Notification (
        val initNotificationTime: Boolean,
        val notificationContent: String,
        val notificationTime: LocalTime
    )

    companion object {
        const val MIN_DAY_OF_WEEK_NUM = 3
    }
}
