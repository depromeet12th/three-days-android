package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal
import java.time.ZoneId
import java.time.ZonedDateTime

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
    )
}

fun GoalEntity.toGoal() = Goal(
    goalId = this.goalId,
    title = this.title,
    startDate = ZonedDateTime.now(ZoneId.systemDefault()),
    endDate = ZonedDateTime.now(ZoneId.systemDefault()),
    startTime = ZonedDateTime.now(ZoneId.systemDefault()),
    notificationTime = ZonedDateTime.now(ZoneId.systemDefault()),
    notificationContent = "",
)
