package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.HabitRepository
import javax.inject.Inject

class GetHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
//    suspend operator fun invoke(habitId: Long): Habit {
//        return habitRepository.getHabit(habitId)
//    }

}
