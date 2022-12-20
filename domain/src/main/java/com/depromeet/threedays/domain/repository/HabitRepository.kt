package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun createHabit(habit: CreateHabit)
    suspend fun getHabits(status: HabitStatus): Flow<DataState<List<Habit>>>
    suspend fun getArchivedHabits(): Flow<DataState<List<Habit>>>
    suspend fun getHabit(habitId: Long): SingleHabit
    suspend fun updateHabit(habitId: Long, habit: CreateHabit)
    suspend fun deleteHabit(habitId: Long): Flow<DataState<Any>>
}
