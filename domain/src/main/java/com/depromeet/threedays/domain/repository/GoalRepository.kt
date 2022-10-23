package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.Goal
import java.time.ZonedDateTime

interface GoalRepository {
    suspend fun findAll(): List<Goal>
    suspend fun findById(goalId: Long): Goal
    suspend fun create(
        title: String,
        startDate: ZonedDateTime? = null,
        endDate: ZonedDateTime? = null,
        startTime: ZonedDateTime? = null,
        notificationTime: ZonedDateTime? = null,
        notificationContent: String? = null,
    )
    suspend fun update(
        goalId: Long,
        title: String,
        startDate: ZonedDateTime? = null,
        endDate: ZonedDateTime? = null,
        startTime: ZonedDateTime? = null,
        notificationTime: ZonedDateTime? = null,
        notificationContent: String? = null,
    )
    suspend fun delete(goalId: Long)
}
