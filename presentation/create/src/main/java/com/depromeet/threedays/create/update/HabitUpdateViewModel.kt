package com.depromeet.threedays.create.update

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.HabitNotification
import com.depromeet.threedays.domain.entity.habit.SingleHabit
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

    private val _oldHabit = MutableStateFlow(
        SingleHabit.EMPTY.copy(
            title = String.Empty,
            emoji = initEmoji,
            dayOfWeeks = emptyList<DayOfWeek>(),
            color = initColor,
            notification = HabitNotification(
                notificationTime = initTime,
                contents = String.Empty
            )
        )
    )
    val oldHabit: StateFlow<SingleHabit>
        get() = _oldHabit.asStateFlow()

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

    private val _notification = MutableStateFlow(
        Notification(
            initNotificationTime = false,
            notificationTime = initTime,
            notificationContent = String.Empty
        )
    )
    val notification: StateFlow<Notification>
        get() = _notification.asStateFlow()

    private val isNotificationInfoActive = MutableStateFlow(true)

    private val _isInformationChanged = MutableStateFlow(false)
    val isInformationChanged: StateFlow<Boolean>
        get() = _isInformationChanged.asStateFlow()

    private var _isUpdateHabitEnable = MutableStateFlow(false)
    val isUpdateHabitEnable: StateFlow<Boolean>
        get() = _isUpdateHabitEnable

    fun getHabit(habitId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                habitRepository.getHabit(habitId = habitId)
            }.onSuccess { habit ->
                _oldHabit.value = habit
                setOldData(habit)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    private fun setOldData(oldData: SingleHabit) {
        title.value = oldData.title
        _emoji.value = oldData.emoji
        _dayOfWeekList.value = oldData.dayOfWeeks
        _color.value = oldData.color
        _notification.value = _notification.value.copy(
            initNotificationTime = oldData.notification != null,
            notificationTime = oldData.notification?.notificationTime ?: initTime,
            notificationContent = oldData.notification?.contents ?: String.Empty
        )
    }

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

    fun setUpdateHabitEnable() {
        viewModelScope.launch {
            combine(
                title,
                dayOfWeekList,
                notification,
                isNotificationInfoActive
            ) { title, dayOfWeekList, notification, isNotificationInfoActive ->
                val notificationEnable =
                    if (isNotificationInfoActive) {
                        notification.notificationContent.isNotEmpty() && notification.initNotificationTime
                    } else true
                title.isNotEmpty() && dayOfWeekList.size >= MIN_DAY_OF_WEEK_NUM && notificationEnable
            }.collect {
                _isUpdateHabitEnable.value = it
            }
        }
    }

    fun setInformationChanged() {
        viewModelScope.launch {
            combine(
                title,
                emoji,
                dayOfWeekList,
                notification,
                color
            ) { title, emoji, dayOfWeekList, notification, color ->
                val isNotificationTimeChanged = if (oldHabit.value.notification == null) {
                    notification.initNotificationTime
                } else {
                    oldHabit.value.notification!!.notificationTime.hour != notification.notificationTime.hour || oldHabit.value.notification!!.notificationTime.minute != notification.notificationTime.minute
                }

                title != oldHabit.value.title || dayOfWeekList != oldHabit.value.dayOfWeeks || emoji != oldHabit.value.emoji ||
                        notification.notificationContent != (oldHabit.value.notification?.contents
                    ?: String.Empty) ||
                        isNotificationTimeChanged || color != oldHabit.value.color
            }.collect {
                _isInformationChanged.value = it
            }
        }
    }

    fun onUpdateHabitClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                val notification = if (isNotificationInfoActive.value) {
                    CreateHabit.Notification(
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
                _action.emit(Action.UpdateClick)
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
        object UpdateClick: Action()
    }

    data class Notification(
        val initNotificationTime: Boolean,
        val notificationContent: String,
        val notificationTime: LocalTime
    )

    companion object {
        const val MIN_DAY_OF_WEEK_NUM = 3
    }
}
