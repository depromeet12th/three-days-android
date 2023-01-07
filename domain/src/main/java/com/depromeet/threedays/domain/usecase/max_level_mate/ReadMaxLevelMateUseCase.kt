package com.depromeet.threedays.domain.usecase.max_level_mate

import com.depromeet.threedays.domain.repository.MaxLevelMateRepository
import javax.inject.Inject

class ReadMaxLevelMateUseCase @Inject constructor(val repository: MaxLevelMateRepository) {
    suspend fun execute(key: String): String? = repository.readMaxLevelMate(key)
}
