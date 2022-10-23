package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal
import java.time.ZonedDateTime

interface GoalRepository {
    suspend fun findAll(): List<Goal>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(
        title: String,
        startDate: ZonedDateTime,
        endDate: ZonedDateTime,
        startTime: ZonedDateTime,
        notificationTime: ZonedDateTime,
        notificationContent: String
    )
    suspend fun update(
        goalId: Long,
        title: String,
        startDate: ZonedDateTime,
        endDate: ZonedDateTime,
        startTime: ZonedDateTime,
        notificationTime: ZonedDateTime,
        notificationContent: String
    )
    suspend fun delete(goalId: Long)
}
