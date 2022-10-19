package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal

interface GoalRepository {
    suspend fun getAllGoals(): List<Goal>
    suspend fun getGoal(goalId: Int): Goal
    suspend fun postGoal(
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    )
    suspend fun updateGoal(
        goalId: Int,
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    )
    suspend fun deleteGoal(goalId: Int)
}
