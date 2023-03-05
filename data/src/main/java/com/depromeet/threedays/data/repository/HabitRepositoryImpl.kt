package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.habit.HabitRemoteDataSource
import com.depromeet.threedays.data.mapper.toHabit
import com.depromeet.threedays.data.mapper.toPostHabitRequest
import com.depromeet.threedays.data.mapper.toSingleHabit
import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitRemoteDataSource: HabitRemoteDataSource
) : HabitRepository {
    override suspend fun createHabit(habit: CreateHabit): Result<SingleHabit> {
        return habitRemoteDataSource.postHabit(request = habit.toPostHabitRequest()).map { it.toSingleHabit() }
    }

    override fun getHabits(status: HabitStatus): Flow<Result<List<Habit>>> =
        flow {
            habitRemoteDataSource.getHabits(
                when (status) {
                    HabitStatus.ACTIVE -> "ACTIVE"
                    HabitStatus.ARCHIVED -> "ARCHIVED"
                    HabitStatus.UNKNOWN -> "UNKNOWN"
                }
            ).onSuccess { response ->
                emit(Result.success(value = response.map { it.toHabit() }))
            }.onFailure { throwable ->
                throwable as ThreeDaysException
                emit(Result.failure(throwable))
            }
        }

    override suspend fun getHabit(habitId: Long): Result<SingleHabit> {
        return habitRemoteDataSource.getHabit(habitId = habitId).map { it.toSingleHabit() }
    }

    override suspend fun updateHabit(habitId: Long, habit: CreateHabit): Result<SingleHabit> {
        return habitRemoteDataSource.updateHabit(habitId = habitId, request = habit.toPostHabitRequest()).map { it.toSingleHabit() }
    }

    override fun deleteHabit(habitId: Long): Flow<Result<Unit>> =
        flow {
            habitRemoteDataSource.deleteHabit(habitId = habitId)
                .onSuccess { response ->
                    emit(Result.success(value = response))
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    emit(Result.failure(throwable))
                }
        }
}
