package com.depromeet.threedays.domain.usecase.onboarding

import com.depromeet.threedays.domain.entity.OnboardingType
import com.depromeet.threedays.domain.repository.OnboardingRepository
import javax.inject.Inject

class WriteOnboardingUseCase @Inject constructor(val repository: OnboardingRepository) {
    suspend fun execute(onboardingType: OnboardingType) {
        val key = when (onboardingType) {
            OnboardingType.NOTIFICATION_RECOMMEND -> OnboardingType.NOTIFICATION_RECOMMEND.key
            OnboardingType.AFTER_SPLASH -> OnboardingType.AFTER_SPLASH.key
            OnboardingType.MATE -> OnboardingType.MATE.key
            OnboardingType.ARCHIVED_HABIT -> OnboardingType.ARCHIVED_HABIT.key
        }
        return repository.writeOnboardnig(key = key, value = "IS_SHWON")
    }
}
