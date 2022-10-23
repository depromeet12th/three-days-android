package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import java.time.ZonedDateTime

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
        startDate = this.startDate.toString(),
        endDate = this.endDate.toString(),
        startTime = this.startTime.toString(),
        notificationTime = this.notificationTime.toString(),
        notificationContent = this.notificationContent,
        status = this.status.toString(),
        createDate = this.createDate.toString(),
        sequence = this.sequence,
        clapIndex = this.clapIndex,
        clapChecked = this.clapChecked,
        lastAchievementDate = this.lastAchievementDate.toString(),
    )
}

fun SaveGoalRequest.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = 0,
        title = this.title,
        startDate = this.startDate.toString(),
        endDate = this.endDate.toString(),
        startTime = this.startTime.toString(),
        notificationTime = this.notificationTime.toString(),
        notificationContent = this.notificationContent,
        status = "",
        createDate = "",
        sequence = 0,
        clapIndex = 0,
        clapChecked = false,
        lastAchievementDate = "",
    )
}

fun GoalEntity.toGoal() = Goal(
    goalId = this.goalId,
    title = this.title,
    startDate = ZonedDateTime.parse(this.startDate),
    endDate = ZonedDateTime.parse(this.endDate),
    startTime = ZonedDateTime.parse(this.startTime),
    notificationTime = ZonedDateTime.parse(this.notificationTime),
    notificationContent = this.notificationContent,
    status = ZonedDateTime.parse(this.status),
    createDate = ZonedDateTime.parse(this.createDate),
    sequence = this.sequence,
    clapIndex = this.clapIndex,
    clapChecked = this.clapChecked,
    lastAchievementDate = ZonedDateTime.parse(this.lastAchievementDate),
)
