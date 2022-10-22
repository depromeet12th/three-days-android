package com.depromeet.threedays.data.datasource

import com.depromeet.threedays.data.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

interface GoalDataSource {
    fun getGoals(): Flow<List<GoalEntity>>
    fun getGoalById(goalId: Long): Flow<GoalEntity>
    suspend fun save(goalEntity: GoalEntity)
    suspend fun deleteById(goalId: Long)
}
