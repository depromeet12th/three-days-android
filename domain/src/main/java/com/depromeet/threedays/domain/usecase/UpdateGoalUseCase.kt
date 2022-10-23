package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(
        goalId: Long,
        title: String,
    ) {
        return goalRepository.update(
            goalId = goalId,
            title = title,
        )
    }
}
