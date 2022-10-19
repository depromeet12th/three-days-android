package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke() {
        return goalRepository.updateGoal(
            goalId = 1,
            title = "updatedTitle",
            startDate = "",
            endDate = "",
            startTime = "",
            notificationTime = "",
            notificationContent = "",
        )
    }
}
