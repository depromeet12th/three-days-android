package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.entity.base.getResult
import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.data.entity.habit.PostHabitRequest
import com.depromeet.threedays.data.entity.habit.SingleHabitEntity
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val habitService: HabitService
) : HabitRemoteDataSource {
    override suspend fun postHabit(request: PostHabitRequest): Result<SingleHabitEntity> {
        return habitService.postHabit(request).getResult()
    }

    override suspend fun getHabits(status: String): Result<List<HabitEntity>> {
        return habitService.getHabits(status).getResult()
    }

    override suspend fun getHabit(habitId: Long): Result<SingleHabitEntity> {
        return habitService.getHabit(habitId = habitId).getResult()
    }

    override suspend fun updateHabit(habitId: Long, request: PostHabitRequest): Result<SingleHabitEntity> {
        return habitService.updateHabit(habitId = habitId, request = request).getResult()
    }

    override suspend fun deleteHabit(habitId: Long): Result<Unit> {
        return habitService.deleteHabit(habitId).getResult()
    }
}
