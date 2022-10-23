package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(goal: Goal) {
        return goalRepository.update(goal)
    }
}
