package com.depromeet.threedays.domain.usecase.habit

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(): Flow<DataState<List<Habit>>> {
        return habitRepository.getHabits(status = HabitStatus.ACTIVE)
    }

}
