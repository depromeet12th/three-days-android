package com.depromeet.threedays.mypage

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Status
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

    /**
     * 내 정보 조회
     */
    fun fetchMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        // Do nothing
                    }
                    Status.SUCCESS -> {
                        _nickname.value = response.data!!.name
                    }
                    Status.ERROR -> TODO()
                    Status.FAIL -> TODO()
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
                when (response.status) {
                    Status.LOADING -> {
                        // Do nothing
                    }
                    Status.SUCCESS -> {
                        _nickname.value = response.data!!.name
                    }
                    Status.ERROR -> TODO()
                    Status.FAIL -> TODO()
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
            }.onFailure {
                sendErrorMessage(it.message ?: "회원탈퇴를 실패했습니다. 잠시 후 다시 시도해주세요.")
            }
        }
    }
}
