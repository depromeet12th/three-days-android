package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(title: String) {
        println("CreateGoalUseCase.title: $title")
        return goalRepository.postGoal(
            title = title,
            startDate = "",
            endDate = "",
            startTime = "",
            notificationTime = "",
            notificationContent = "",
        )
    }
}
