package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.habit.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun createHabit(habit: Habit)
    suspend fun getHabits(): Flow<DataState<List<Habit>>>
    //suspend fun getHabit(habitId: Long): Habit
    suspend fun updateHabit(habitId: Long)
    suspend fun deleteHabit(habitId: Long)
}