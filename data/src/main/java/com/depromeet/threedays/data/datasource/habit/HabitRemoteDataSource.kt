package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.request.PostHabitRequest

interface HabitRemoteDataSource {
    suspend fun postHabit(request: PostHabitRequest)
    suspend fun getHabits(): List<HabitEntity>
    suspend fun getArchivedHabits(): List<HabitEntity>
    //fun getHabit(habitId: Long): HabitEntity
    suspend fun updateHabit(habitId: Long)
    suspend fun deleteHabit(habitId: Long): Any
}
