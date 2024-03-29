package com.depromeet.threedays.domain.usecase.habit

import com.depromeet.threedays.domain.entity.HabitStatus
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchivedHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(): Flow<Result<List<Habit>>> {
        return habitRepository.getHabits(
            status = HabitStatus.ARCHIVED,
        )
    }
}
