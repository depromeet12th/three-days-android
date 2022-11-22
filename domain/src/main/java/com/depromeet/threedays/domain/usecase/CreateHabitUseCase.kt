package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.HabitRepository
import javax.inject.Inject

class CreateHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit) {
        return habitRepository.createHabit(habit)
    }
}
