package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.request.SaveGoalRequest
import com.depromeet.threedays.domain.repository.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(goal: SaveGoalRequest) {
        return goalRepository.create(goal)
    }
}
