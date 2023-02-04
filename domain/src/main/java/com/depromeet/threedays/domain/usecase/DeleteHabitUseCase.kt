package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {
    operator fun invoke(habitId: Long): Flow<Result<Unit>> {
        return habitRepository.deleteHabit(habitId)
    }
}
