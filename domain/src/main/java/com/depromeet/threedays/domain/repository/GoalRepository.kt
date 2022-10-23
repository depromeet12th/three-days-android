package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal

interface GoalRepository {
    suspend fun findAll(): List<Goal>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(goal: Goal)
    suspend fun update(goal: Goal)
    suspend fun delete(goalId: Long)
}
