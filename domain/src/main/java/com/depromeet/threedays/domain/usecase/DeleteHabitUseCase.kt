package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long): Flow<DataState<Any>> {
        return habitRepository.deleteHabit(habitId)
    }
}
