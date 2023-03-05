package com.depromeet.threedays.mypage

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.usecase.member.GetMyInfoUseCase
import com.depromeet.threedays.domain.usecase.member.LogoutUseCase
import com.depromeet.threedays.domain.usecase.member.UpdateNicknameUseCase
import com.depromeet.threedays.domain.usecase.member.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val updateNicknameUseCase: UpdateNicknameUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel() {
    private val _nickname: MutableStateFlow<String> = MutableStateFlow(String.Empty)
    val nickname: StateFlow<String>
        get() = _nickname

    private val _logoutSucceed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val logoutSucceed: StateFlow<Boolean>
        get() = _logoutSucceed

    private val _signoutSucceed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val signoutSucceed: StateFlow<Boolean>
        get() = _signoutSucceed

    init {
        fetchMyInfo()
    }

    /**
     * 내 정보 조회
     */
    private fun fetchMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase().collect { response ->
                response.onSuccess {
                    _nickname.value = it.name
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    /**
     * 닉네임 수정
     */
    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            updateNicknameUseCase(nickname = nickname).collect { response ->
                response.onSuccess {
                    _nickname.value = it.name
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    /**
     * 로그아웃
     */
    fun logout() {
        viewModelScope.launch {
            kotlin.runCatching {
                logoutUseCase()
            }.onSuccess {
                _logoutSucceed.emit(true)
            }.onFailure {
                sendErrorMessage(it.message ?: "로그아웃에 실패했습니다. 잠시 후 다시 시도해주세요.")
            }
        }
    }

    /**
     * 회원 탈퇴
     */
    fun withdraw() {
        viewModelScope.launch {
            kotlin.runCatching {
                withdrawUseCase()
            }.onSuccess {
                _signoutSucceed.emit(true)
            }.onFailure {
                sendErrorMessage(it.message ?: "회원탈퇴를 실패했습니다. 잠시 후 다시 시도해주세요.")
            }
        }
    }
}
