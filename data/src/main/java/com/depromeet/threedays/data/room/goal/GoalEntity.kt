package com.depromeet.threedays.data.room.goal

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME_GOAL = "GOAL"

@Entity(tableName = TABLE_NAME_GOAL)
class GoalEntity (
    @PrimaryKey
    val goalId: Long = 0,
    var title: String,
) {
}
