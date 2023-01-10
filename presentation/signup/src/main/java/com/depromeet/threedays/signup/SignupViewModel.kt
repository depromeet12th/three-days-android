package com.depromeet.threedays.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.auth.SignupMember
import com.depromeet.threedays.domain.entity.member.AuthenticationProvider
import com.depromeet.threedays.domain.usecase.auth.CreateMemberUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createMemberUserCase: CreateMemberUserCase,
) : BaseViewModel() {

   private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess.distinctUntilChanged()

    fun createMember(certificationSubject: AuthenticationProvider = AuthenticationProvider.KAKAO, socialToken: String) {
        viewModelScope.launch {
            createMemberUserCase.invoke(
                certificationSubject = certificationSubject,
                socialToken = socialToken
            ).runCatching {
                this.getOrThrow()
            }.onSuccess { value: SignupMember ->
                _action.emit(
                    if(value.isSignedUp) Action.AlreadySignedUp
                    else Action.FirstSignup
                )
                Timber.i("--- SignupViewModel - token : ${value.token}")
            }.onFailure { throwable ->
                Timber.e("--- SignupViewModel - Signup error: ${throwable.message}")
            }
        }
    }

    sealed class Action{
        object FirstSignup: Action()
        object AlreadySignedUp: Action()
    }
}
