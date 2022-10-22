package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal

interface GoalRepository {
    suspend fun findAll(): List<Goal>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    )
    suspend fun update(
        goalId: Long,
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    )
    suspend fun delete(goalId: Long)
}
