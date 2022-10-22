package com.depromeet.threedays.data.datasource

import com.depromeet.threedays.data.db.GoalDao
import com.depromeet.threedays.data.entity.GoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalGoalDataSource @Inject constructor(
    private val goalDao: GoalDao
) : GoalDataSource {
    override fun getGoals(): Flow<List<GoalEntity>> = goalDao.selectGoals()

    override fun getGoalById(goalId: Long): Flow<GoalEntity> = goalDao.selectByGoalId(goalId)

    override suspend fun save(goalEntity: GoalEntity) = goalDao.insert(goalEntity)

    override suspend fun deleteById(goalId: Long) = goalDao.deleteById(goalId)
}
