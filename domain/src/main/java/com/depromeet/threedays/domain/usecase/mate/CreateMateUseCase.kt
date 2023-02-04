package com.depromeet.threedays.domain.usecase.mate

import com.depromeet.threedays.domain.entity.mate.CreateMate
import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.repository.MateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateMateUseCase @Inject constructor(
    private val mateRepository: MateRepository
) {
    suspend operator fun invoke(habitId: Long, mate: CreateMate): Flow<Result<Mate>> {
        return mateRepository.createMate(habitId, mate)
    }

}
