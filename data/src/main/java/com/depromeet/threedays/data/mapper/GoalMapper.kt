package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        startTime = this.startTime,
        notificationTime = this.notificationTime,
        notificationContent = this.notificationContent,
        status = this.status,
        createDate = this.createDate,
        sequence = this.sequence,
        clapIndex = this.clapIndex,
        clapChecked = this.clapChecked,
        lastAchievementDate = this.lastAchievementDate,
    )
}

fun GoalEntity.toGoal() = Goal(
    goalId = this.goalId,
    title = this.title,
    startDate = this.startDate,
    endDate = this.endDate,
    startTime = this.startTime,
    notificationTime = this.notificationTime,
    notificationContent = this.notificationContent,
    status = this.status,
    createDate = this.createDate,
    sequence = this.sequence,
    clapIndex = this.clapIndex,
    clapChecked = this.clapChecked,
    lastAchievementDate = this.lastAchievementDate,
)
