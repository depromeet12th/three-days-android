package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.data.entity.habit.PostHabitRequest
import com.depromeet.threedays.data.entity.habit.SingleHabitEntity
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val habitService: HabitService
) : HabitRemoteDataSource {
    override suspend fun postHabit(request: PostHabitRequest) {
        return habitService.postHabit(request)
    }

    override suspend fun getHabits(status: String): List<HabitEntity> {
        return habitService.getHabits(status).data ?: emptyList()
    }

    override suspend fun getHabit(habitId: Long): SingleHabitEntity {
        return habitService.getHabit(habitId = habitId).data ?: throw IllegalStateException()
    }

    override suspend fun updateHabit(habitId: Long, request: PostHabitRequest) {
        return habitService.updateHabit(habitId = habitId, request = request)
    }

    override suspend fun deleteHabit(habitId: Long) {
        habitService.deleteHabit(habitId)
    }
}
