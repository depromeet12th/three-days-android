package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.entity.HabitEntity

interface HabitRemoteDataSource {
    suspend fun postHabit(habitEntity: HabitEntity)
    suspend fun getHabits(): List<HabitEntity>
    suspend fun getArchivedHabits(): List<HabitEntity>
    //fun getHabit(habitId: Long): HabitEntity
    suspend fun updateHabit(habitId: Long)
    suspend fun deleteHabit(habitId: Long): Any
}
