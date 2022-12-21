package com.depromeet.threedays.mypage

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.member.GetMyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
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
}
