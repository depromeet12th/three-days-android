package com.depromeet.threedays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME_GOAL = "GOAL"

@Entity(tableName = TABLE_NAME_GOAL)
class GoalEntity (
    @PrimaryKey(autoGenerate = true)
    val goalId: Long = 0,
    var title: String,
) {
}
