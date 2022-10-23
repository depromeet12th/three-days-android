package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.entity.GoalEntity
import com.depromeet.threedays.data.mapper.toGoal
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.repository.GoalRepository
import kotlinx.coroutines.flow.first
import java.time.ZonedDateTime
import javax.inject.Inject

class LocalGoalRepository @Inject constructor(
    private val goalDataSource: GoalDataSource
) : GoalRepository {
    override suspend fun findAll(): List<Goal> {
        return goalDataSource.getGoals().first().map { it.toGoal() }
    }

    override suspend fun findById(goalId: Long): Goal {
        return goalDataSource.getGoalById(goalId).first().toGoal()
    }

    override suspend fun create(
        title: String,
        startDate: ZonedDateTime,
        endDate: ZonedDateTime,
        startTime: ZonedDateTime,
        notificationTime: ZonedDateTime,
        notificationContent: String
    ) {
        goalDataSource.save(
            GoalEntity(title = title)
        )
    }

    override suspend fun update(
        goalId: Long,
        title: String,
        startDate: ZonedDateTime,
        endDate: ZonedDateTime,
        startTime: ZonedDateTime,
        notificationTime: ZonedDateTime,
        notificationContent: String
    ) {
        goalDataSource.save(
            GoalEntity(goalId = goalId, title = title)
        )
    }

    override suspend fun delete(goalId: Long) {
        goalDataSource.deleteById(goalId)
    }
}
