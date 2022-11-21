package com.depromeet.threedays.domain.entity.habit

import java.time.ZonedDateTime

data class UpdateGoalRequest (
    val goalId: Long,
    val title: String,
    val startDate: ZonedDateTime? = null,
    val endDate: ZonedDateTime? = null,
    val startTime: ZonedDateTime? = null,
    val notificationTime: ZonedDateTime? = null,
    val notificationContent: String? = null,
    val sequence: Int,
    val lastAchievementDate: ZonedDateTime? = null,
    // 0,1,2 중에 오늘 몇번째 짝! 누를수 있는지
    var clapIndex: Int,
    // 오늘꺼 눌렀는지 아닌지
    var clapChecked: Boolean
)
