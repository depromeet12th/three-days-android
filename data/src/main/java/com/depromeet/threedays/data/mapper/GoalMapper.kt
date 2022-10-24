package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest
import java.time.ZonedDateTime

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
        startDate = this.startDate?.toString(),
        endDate = this.endDate?.toString(),
        startTime = this.startTime?.toString(),
        notificationTime = this.notificationTime?.toString(),
        notificationContent = this.notificationContent,
        status = this.status?.toString(),
        createDate = this.createDate?.toString(),
        sequence = this.sequence,
        clapIndex = this.clapIndex,
        clapChecked = this.clapChecked,
        lastAchievementDate = this.lastAchievementDate?.toString(),
    )
}

fun SaveGoalRequest.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = 0,
        title = this.title,
        startDate = if(this.startDate == null) null else this.startDate.toString(),
        endDate = if(this.endDate == null) null else this.endDate.toString(),
        startTime = if(this.startTime == null) null else this.startTime.toString(),
        notificationTime = if(this.notificationTime == null) null else this.notificationTime.toString(),
        notificationContent = this.notificationContent,
        status = "",
        createDate = "",
        sequence = 0,
        clapIndex = 0,
        clapChecked = false,
        lastAchievementDate = "",
    )
}

fun UpdateGoalRequest.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
        startDate = if(this.startDate == null) null else this.startDate.toString(),
        endDate = if(this.endDate == null) null else this.endDate.toString(),
        startTime = if(this.startTime == null) null else this.startTime.toString(),
        notificationTime = if(this.notificationTime == null) null else this.notificationTime.toString(),
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
    startDate = if(this.startDate.isNullOrEmpty()) null else ZonedDateTime.parse(this.startDate),
    endDate = if(this.endDate.isNullOrEmpty()) null else ZonedDateTime.parse(this.endDate),
    startTime = if(this.startTime.isNullOrEmpty()) null else ZonedDateTime.parse(this.startTime),
    notificationTime = if(this.notificationTime.isNullOrEmpty()) null else ZonedDateTime.parse(this.notificationTime),
    notificationContent = this.notificationContent,
    status = if(this.status.isNullOrEmpty()) null else ZonedDateTime.parse(this.status),
    createDate = if(this.createDate.isNullOrEmpty()) null else ZonedDateTime.parse(this.createDate),
    sequence = this.sequence,
    clapIndex = this.clapIndex,
    clapChecked = this.clapChecked,
    lastAchievementDate = if(this.lastAchievementDate.isNullOrEmpty()) null else ZonedDateTime.parse(this.lastAchievementDate),
)
