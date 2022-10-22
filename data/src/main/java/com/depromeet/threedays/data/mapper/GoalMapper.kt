package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.domain.entity.Goal

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        goalId = this.goalId,
        title = this.title,
    )
}

fun GoalEntity.toGoal() = Goal(
    goalId = this.goalId,
    title = this.title,
    startDate = "",
    endDate = "",
    startTime = "",
    notificationTime = "",
    notificationContent = "",
    status = "",
    createDate = "",
    sequence = "",
    lastAchievementDate = "",
)
