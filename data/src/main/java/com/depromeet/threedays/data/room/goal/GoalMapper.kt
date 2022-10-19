package com.depromeet.threedays.data.room.goal

import com.depromeet.threedays.domain.entity.Goal

fun Goal.toGoalEntity(): GoalEntity {
    return GoalEntity(
        title = this.title,
    )
}

fun GoalEntity.toGoal(): Goal {
    TODO()
}
