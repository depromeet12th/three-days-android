package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(title: String) {
        return goalRepository.create(
            title = title,
            startDate = "",
            endDate = "",
            startTime = "",
            notificationTime = "",
            notificationContent = "",
        )
    }
}
