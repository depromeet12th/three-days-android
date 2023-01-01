package com.depromeet.threedays.domain.usecase.achievement

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateHabitAchievementUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(habitId: Long): Flow<DataState<Habit>> {
        return achievementRepository.createHabitAchievement(habitId)
    }

}
