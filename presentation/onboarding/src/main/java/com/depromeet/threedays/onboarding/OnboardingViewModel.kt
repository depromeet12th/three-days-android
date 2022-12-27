package com.depromeet.threedays.onboarding

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.usecase.onboarding.WriteOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val writeOnboardingUseCase: WriteOnboardingUseCase,
) : BaseViewModel() {
    fun writeIsFirstVisitor() {
        viewModelScope.launch {
            writeOnboardingUseCase.execute(IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH, "false")
        }
    }

    companion object {
        private const val IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH = "IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH"
    }
}
