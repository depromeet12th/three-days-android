package com.depromeet.threedays.splash

import com.depromeet.threedays.domain.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.usecase.onboarding.ReadOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val readOnboardingUseCase: ReadOnboardingUseCase,
) : BaseViewModel() {

    private val _isFirstVisitor: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFirstVisitor: StateFlow<Boolean>
        get() = _isFirstVisitor

    init {
        checkIsFirstVisitor()
    }

    private fun checkIsFirstVisitor() {
        viewModelScope.launch {
            val response = readOnboardingUseCase.execute(IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH)
            _isFirstVisitor.update {
                (response == null || response == "true")
            }
        }
    }
    
    fun isSignedUp(): Boolean {
        return authRepository.getAccessTokenFromLocal().isNotEmpty()
    }

    companion object {
        private const val IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH = "IS_FIRST_VISIT_ONBOARDING_AFTER_SPLASH"
    }
}
