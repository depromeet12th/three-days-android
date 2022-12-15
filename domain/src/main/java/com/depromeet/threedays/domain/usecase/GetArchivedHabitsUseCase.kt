package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchivedHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {
    suspend operator fun invoke(): Flow<DataState<List<Habit>>> {
        return habitRepository.getArchivedHabits()
    }
}
