package com.depromeet.threedays.domain.usecase.onboarding

import com.depromeet.threedays.domain.repository.OnboardingRepository
import javax.inject.Inject

class ReadOnboardingUseCase @Inject constructor(val repository: OnboardingRepository) {
    suspend fun execute(key: String): String? = repository.readOnboardnig(key)
}
