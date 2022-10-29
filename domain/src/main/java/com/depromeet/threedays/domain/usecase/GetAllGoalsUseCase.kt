package com.depromeet.threedays.domain.usecase

import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGoalsUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(): Flow<List<Goal>> {
        return goalRepository.findAll()
    }

}
