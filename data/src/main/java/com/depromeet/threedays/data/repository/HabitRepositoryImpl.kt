package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSource
import com.depromeet.threedays.data.mapper.toHabit
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitRemoteDataSource: HabitRemoteDataSource
) : HabitRepository {
    override suspend fun createHabit(habit: Habit) {

    }

    override suspend fun getHabits(): Flow<DataState<List<Habit>>> =
        flow {
            emit(DataState.loading())
            val response = habitRemoteDataSource.getHabits()

            if(response.isNotEmpty()) {
                emit(DataState.success(data = response.map { it.toHabit() }))
            } else {
                emit(DataState.error(msg = "response has error"))
            }.runCatching {
                emit(DataState.fail("response is fail"))
            }
        }

//    override suspend fun getHabit(habitId: Long): Habit {
//
//    }

    override suspend fun updateHabit(habitId: Long) {

    }

    override suspend fun deleteHabit(habitId: Int): Flow<DataState<Any>> =
        flow {
            emit(DataState.loading())
            val response = habitRemoteDataSource.deleteHabit(habitId)

            if(response != null) {
                emit(DataState.success(data = response))
            } else {
                emit(DataState.error(msg = "response has error"))
            }.runCatching {
                emit(DataState.fail("response is fail"))
            }
        }
}
