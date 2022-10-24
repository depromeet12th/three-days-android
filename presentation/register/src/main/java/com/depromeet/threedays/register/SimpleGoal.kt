package com.depromeet.threedays.register

import com.depromeet.threedays.domain.entity.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.ZonedDateTime

data class SimpleGoal(
    val goalId: Long,
    val title: MutableStateFlow<String>,
    var startDate: ZonedDateTime?,
    var endDate: ZonedDateTime?,
    var startTime: ZonedDateTime?,
    var notificationTime: ZonedDateTime?,
    var notificationContent: String?
) {
    companion object {
        val EMPTY = SimpleGoal(
            goalId = 0,
            title = MutableStateFlow(""),
            startDate = null,
            endDate = null,
            startTime = null,
            notificationTime = null,
            notificationContent = ""
        )
    }
}

fun Goal.toSimpleGoal() = SimpleGoal(
    goalId = this.goalId,
    title = MutableStateFlow(this.title),
    startDate = this.startDate,
    endDate = this.endDate,
    startTime = this.startTime,
    notificationTime = this.notificationTime,
    notificationContent = this.notificationContent
)
