package com.depromeet.threedays.signup.complete

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.repository.NotificationTokenRepository
import com.depromeet.threedays.domain.usecase.notification.token.UpdateNotificationTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupCompleteViewModel @Inject constructor(
    private val notificationTokenRepository: NotificationTokenRepository,
    private val updateNotificationTokenUseCase: UpdateNotificationTokenUseCase,
) : BaseViewModel() {

    /**
     * 회원 가입 성공시 fcmToken 을 서버로 전송
     */
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
