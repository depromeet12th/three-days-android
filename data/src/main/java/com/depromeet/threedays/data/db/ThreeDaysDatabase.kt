package com.depromeet.threedays.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.depromeet.threedays.data.entity.GoalEntity

const val DATABASE_NAME_THREE_DAYS = "THREE_DAYS"

@Database(entities = [GoalEntity::class], version = 2)
abstract class ThreeDaysDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
}
