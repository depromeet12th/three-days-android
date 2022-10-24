package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.mapper.toGoal
import com.depromeet.threedays.data.mapper.toGoalEntity
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest
import com.depromeet.threedays.domain.repository.GoalRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDataSource: GoalDataSource
) : GoalRepository {
    override suspend fun findAll(): List<Goal> {
        return goalDataSource.getGoals().first().map { it.toGoal() }
    }

    override suspend fun findById(goalId: Long): Goal {
        return goalDataSource.getGoalById(goalId).first().toGoal()
    }

    override suspend fun create(goal: SaveGoalRequest) {
        goalDataSource.save(goal.toGoalEntity())
    }

    override suspend fun update(goal: UpdateGoalRequest) {
        goalDataSource.save(goal.toGoalEntity())
    }

    override suspend fun delete(goalId: Long) {
        goalDataSource.deleteById(goalId)
    }
}
