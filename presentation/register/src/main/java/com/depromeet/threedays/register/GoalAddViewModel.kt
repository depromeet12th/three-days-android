package com.depromeet.threedays.register

import com.depromeet.threedays.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.ZoneId
import java.time.ZonedDateTime

class GoalAddViewModel : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    data class State(
        val day: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        val goal: SimpleGoal = SimpleGoal(
            startDate = String.format("%d. %d. %d", day.year, day.monthValue, day.dayOfMonth),
            endDate = String.format("%d. %d. %d", day.year, day.monthValue, day.dayOfMonth)
        )
    )

    data class SimpleGoal(
        var title: MutableStateFlow<String> = MutableStateFlow(""),
        var startDate: String = "",
        var endDate: String = "",
        var startTime: String = "",
        var notificationTime: String = "",
        var notificationContent: String = ""
    )
}
