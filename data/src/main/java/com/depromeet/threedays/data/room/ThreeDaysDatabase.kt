package com.depromeet.threedays.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.depromeet.threedays.data.room.goal.GoalDao
import com.depromeet.threedays.data.room.goal.GoalEntity

const val DATABASE_NAME_THREE_DAYS = "THREE_DAYS"

@Database(entities = [GoalEntity::class], version = 1)
abstract class ThreeDaysDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
}
