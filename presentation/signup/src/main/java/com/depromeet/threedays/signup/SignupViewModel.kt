package com.depromeet.threedays.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.repository.NotificationTokenRepository
import com.depromeet.threedays.domain.usecase.auth.CreateMemberUserCase
import com.depromeet.threedays.domain.usecase.notification.token.UpdateNotificationTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createMemberUserCase: CreateMemberUserCase,
    private val notificationTokenRepository: NotificationTokenRepository,
    private val updateNotificationTokenUseCase: UpdateNotificationTokenUseCase,
) : BaseViewModel() {

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess.distinctUntilChanged()

    fun createMember(certificationSubject: AuthenticationProvider = AuthenticationProvider.KAKAO, socialToken: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                createMemberUserCase.invoke(
                    certificationSubject = certificationSubject,
                    socialToken = socialToken
                )
            }.onSuccess {
                Timber.i("--- SignupViewModel - token: ${it.getOrNull()?.token}")
                _isSuccess.value = true
            }.onFailure { throwable ->
                Timber.e("--- SignupViewModel - Signup error: ${throwable.message}")
            }
        }
    }

    fun updateFcmToken() {
        viewModelScope.launch {
            notificationTokenRepository.getToken()
                ?.runCatching {
                    updateNotificationTokenUseCase(notificationToken = this)
                }?.onFailure { e ->
                    Timber.e(e, "Failed to update registration token")
                }
        }
    }
}
