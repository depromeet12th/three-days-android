package com.depromeet.threedays.onboarding

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.OnboardingType
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
            writeOnboardingUseCase.execute(OnboardingType.AFTER_SPLASH)
        }
    }
}
