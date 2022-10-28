package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun findAll(): Flow<List<Goal>>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(goal: SaveGoalRequest)
    suspend fun update(goal: UpdateGoalRequest)
    suspend fun delete(goalId: Long)
}
