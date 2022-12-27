package com.depromeet.threedays.domain.usecase.onboarding

import com.depromeet.threedays.domain.repository.OnboardingRepository
import javax.inject.Inject

class WriteOnboardingUseCase @Inject constructor(val repository: OnboardingRepository) {
    suspend fun execute(key: String, value: String) = repository.writeOnboardnig(key = key, value = value)
}
