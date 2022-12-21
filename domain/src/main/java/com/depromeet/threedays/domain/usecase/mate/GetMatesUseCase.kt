package com.depromeet.threedays.domain.usecase.mate

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.entity.mate.Mate
import com.depromeet.threedays.domain.repository.MateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatesUseCase @Inject constructor(
    private val mateRepository: MateRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<Mate>>> {
        return mateRepository.getMates()
    }

}
