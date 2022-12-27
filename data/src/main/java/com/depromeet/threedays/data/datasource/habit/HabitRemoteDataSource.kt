package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.data.entity.habit.PostHabitRequest
import com.depromeet.threedays.data.entity.habit.SingleHabitEntity

interface HabitRemoteDataSource {
    suspend fun postHabit(request: PostHabitRequest)
    suspend fun getHabits(status: String): List<HabitEntity>
    suspend fun getHabit(habitId: Long): SingleHabitEntity
    suspend fun updateHabit(habitId: Long, request: PostHabitRequest)
    suspend fun deleteHabit(habitId: Long)
}
