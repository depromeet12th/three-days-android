package com.depromeet.threedays.register

import kotlinx.coroutines.flow.MutableStateFlow
import java.time.ZonedDateTime

data class SimpleGoal(
    val goalId: Long,
    val title: MutableStateFlow<String>,
    var startDate: ZonedDateTime?,
    var endDate: ZonedDateTime?,
    var startTime: ZonedDateTime?,
    var notificationTime: ZonedDateTime?,
    var notificationContent: String?,
    val sequence: Int,
    val lastAchievementDate: ZonedDateTime? = null,
    // 0,1,2 중에 오늘 몇번째 짝! 누를수 있는지
    var clapIndex: Int = 0,
    // 오늘꺼 눌렀는지 아닌지
    var clapChecked: Boolean = false
) {
    companion object {
        val EMPTY = SimpleGoal(
            goalId = 0,
            title = MutableStateFlow(""),
            startDate = null,
            endDate = null,
            startTime = null,
            notificationTime = null,
            notificationContent = "",
            sequence = 0,
            lastAchievementDate = null,
            clapIndex = 0,
            clapChecked = false,
        )
    }
}

//fun Habit.toSimpleGoal() = SimpleGoal(
//    goalId = this.goalId,
//    title = MutableStateFlow(this.title),
//    startDate = this.startDate,
//    endDate = this.endDate,
//    startTime = this.startTime,
//    notificationTime = this.notificationTime,
//    notificationContent = this.notificationContent,
//    sequence = this.sequence,
//    clapIndex = this.clapIndex,
//    clapChecked = this.clapChecked,
//    lastAchievementDate = this.lastAchievementDate,
//)
