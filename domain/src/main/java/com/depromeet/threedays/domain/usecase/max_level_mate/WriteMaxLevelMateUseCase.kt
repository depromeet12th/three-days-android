package com.depromeet.threedays.domain.usecase.max_level_mate

import com.depromeet.threedays.domain.repository.MaxLevelMateRepository
import javax.inject.Inject

class WriteMaxLevelMateUseCase @Inject constructor(val repository: MaxLevelMateRepository) {
    suspend fun execute(key: String, value: String) = repository.writeMaxLevelMate(key = key, value = value)
}
