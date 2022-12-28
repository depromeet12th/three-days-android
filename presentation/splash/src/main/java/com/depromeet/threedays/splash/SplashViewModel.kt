package com.depromeet.threedays.splash

import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    fun isSignedUp(): Boolean {
        return authRepository.getAccessTokenFromLocal().isNotEmpty()
    }
}
