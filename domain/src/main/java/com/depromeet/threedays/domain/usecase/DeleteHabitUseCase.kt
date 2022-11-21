package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.HabitRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long) {
        return habitRepository.deleteHabit(habitId)
    }
}
