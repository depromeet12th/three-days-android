package com.depromeet.threedays.data.room.goal

import com.depromeet.threedays.data.datasource.GoalDataSource
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
