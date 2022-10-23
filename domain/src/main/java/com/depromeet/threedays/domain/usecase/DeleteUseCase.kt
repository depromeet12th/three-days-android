package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class DeleteGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(goalId: Long) {
        return goalRepository.delete(goalId)
    }
}
