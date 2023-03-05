package com.depromeet.threedays.domain.repository

import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun createHabit(habit: CreateHabit): Result<SingleHabit>
    fun getHabits(status: HabitStatus): Flow<Result<List<Habit>>>
    suspend fun getHabit(habitId: Long): Result<SingleHabit>
    suspend fun updateHabit(habitId: Long, habit: CreateHabit): Result<SingleHabit>
    fun deleteHabit(habitId: Long): Flow<Result<Unit>>
}
