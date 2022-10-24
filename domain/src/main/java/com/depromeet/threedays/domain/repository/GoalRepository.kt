package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest

interface GoalRepository {
    suspend fun findAll(): List<Goal>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(goal: SaveGoalRequest)
    suspend fun update(goal: UpdateGoalRequest)
    suspend fun delete(goalId: Long)
}
