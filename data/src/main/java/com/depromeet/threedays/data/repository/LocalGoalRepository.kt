package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.room.goal.GoalEntity
import com.depromeet.threedays.data.room.goal.toGoal
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.repository.GoalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalGoalRepository @Inject constructor(
    private val goalDataSource: GoalDataSource
) : GoalRepository {
    override suspend fun getAllGoals(): List<Goal> {
        return goalDataSource.getGoals().first().map { it.toGoal() }
    }

    override suspend fun getGoal(goalId: Int): Goal {
        return goalDataSource.getGoalById(goalId.toLong()).first().toGoal()
    }

    override suspend fun postGoal(
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    ) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            goalDataSource.save(
                GoalEntity(title = title)
            )
        }
    }

    override suspend fun updateGoal(
        goalId: Int,
        title: String,
        startDate: String,
        endDate: String,
        startTime: String,
        notificationTime: String,
        notificationContent: String
    ) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            goalDataSource.save(
                GoalEntity(goalId = goalId.toLong(), title = title)
            )
        }
    }

    override suspend fun deleteGoal(goalId: Int) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            goalDataSource.deleteById(goalId.toLong())
        }
    }
}
