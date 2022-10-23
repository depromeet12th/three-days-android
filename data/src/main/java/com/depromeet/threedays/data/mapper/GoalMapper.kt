package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal
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

fun GoalEntity.toGoal() = Goal(
    goalId = this.goalId,
    title = this.title,
    notificationContent = this.notificationContent,
    sequence = this.sequence,
    clapIndex = this.clapIndex,
    clapChecked = this.clapChecked,
)
