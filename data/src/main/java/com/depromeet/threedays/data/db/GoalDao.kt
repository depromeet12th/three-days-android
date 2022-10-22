package com.depromeet.threedays.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.data.entity.TABLE_NAME_GOAL
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM $TABLE_NAME_GOAL")
    fun selectGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM $TABLE_NAME_GOAL WHERE goalId = :goalId")
    fun selectByGoalId(goalId: Long): Flow<GoalEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(goalEntity: GoalEntity)

    @Update
    suspend fun update(goalEntity: GoalEntity)

    @Query("DELETE FROM $TABLE_NAME_GOAL WHERE goalId = :goalId")
    suspend fun deleteById(goalId: Long)
}
