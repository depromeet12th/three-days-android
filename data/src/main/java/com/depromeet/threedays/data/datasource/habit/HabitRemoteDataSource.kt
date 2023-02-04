package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.data.entity.habit.PostHabitRequest
import com.depromeet.threedays.data.entity.habit.SingleHabitEntity

interface HabitRemoteDataSource {
    suspend fun postHabit(request: PostHabitRequest): Result<SingleHabitEntity>
    suspend fun getHabits(status: String): Result<List<HabitEntity>>
    suspend fun getHabit(habitId: Long): Result<SingleHabitEntity>
    suspend fun updateHabit(habitId: Long, request: PostHabitRequest): Result<SingleHabitEntity>
    suspend fun deleteHabit(habitId: Long): Result<Unit>
}
