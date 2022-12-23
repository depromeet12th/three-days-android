package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSource
import com.depromeet.threedays.data.mapper.toHabit
import com.depromeet.threedays.data.mapper.toPostHabitRequest
import com.depromeet.threedays.data.mapper.toSingleHabit
import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitRemoteDataSource: HabitRemoteDataSource
) : HabitRepository {
    override suspend fun createHabit(habit: CreateHabit) {
        return habitRemoteDataSource.postHabit(request = habit.toPostHabitRequest())
    }

    override fun getHabits(status: HabitStatus): Flow<DataState<List<Habit>>> =
        flow {
            run {
                emit(DataState.loading())
                val response = habitRemoteDataSource.getHabits(
                    when (status) {
                        HabitStatus.ACTIVE -> "ACTIVE"
                        HabitStatus.ARCHIVED -> "ARCHIVED"
                        HabitStatus.UNKNOWN -> "UNKNOWN"
                    }
                )
                emit(DataState.success(data = response.map { it.toHabit() }))
            }.runCatching {
                emit(DataState.fail("response is fail"))
            }
        }

    override suspend fun getHabit(habitId: Long): SingleHabit {
        return habitRemoteDataSource.getHabit(habitId = habitId).toSingleHabit()
    }

    override suspend fun updateHabit(habitId: Long, habit: CreateHabit) {
        return habitRemoteDataSource.updateHabit(habitId = habitId, request = habit.toPostHabitRequest())
    }

    override fun deleteHabit(habitId: Long): Flow<DataState<Unit>> =
        flow {
            run {
            emit(DataState.loading())
            habitRemoteDataSource.deleteHabit(habitId = habitId).apply {
                emit(DataState.success(data = this))
            }
            }.runCatching {
                emit(DataState.error(msg = "response has error"))
            }
        }
}
