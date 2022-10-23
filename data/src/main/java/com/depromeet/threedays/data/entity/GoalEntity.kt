package com.depromeet.threedays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME_GOAL = "GOAL"

@Entity(tableName = TABLE_NAME_GOAL)
class GoalEntity (
    @PrimaryKey(autoGenerate = true)
    val goalId: Long = 0,
    val title: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val startTime: String? = null,
    val notificationTime: String? = null,
    val notificationContent: String? = null,
    val status: String? = null,
    val createDate: String? = null,
    val sequence: Int,
    val lastAchievementDate: String? = null,

    // 0,1,2 중에 오늘 몇번째 짝! 누를수 있는지
    var clapIndex: Int = 0,
    // 오늘꺼 눌렀는지 아닌지
    var clapChecked: Boolean = false
)
