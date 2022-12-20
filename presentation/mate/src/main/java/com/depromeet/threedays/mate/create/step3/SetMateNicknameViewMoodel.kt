package com.depromeet.threedays.mate.create.step3

import com.depromeet.threedays.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_design

@HiltViewModel
class SetMateNicknameViewMoodel @Inject constructor(
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun handleInputText(inputText: String) {
        if(inputText.length <= NICKNAME_MAX_LENGTH) {
            _uiState.update {
                it.copy(
                    inputText = inputText,
                    inputTextLength = inputText.length.toString(),
                    buttonBackgroundRes = if(inputText.isEmpty()) {
                        core_design.drawable.bg_rect_gray200_r15
                    } else {
                        core_design.drawable.bg_rect_gray800_r15
                    },
                    buttonTextColor = if(inputText.isEmpty()) {
                        core_design.color.gray_450
                    } else {
                        core_design.color.white
                    },
                    isGuideVisible = inputText.isNotEmpty()
                )
            }
        }
    }

    companion object {
        const val NICKNAME_MAX_LENGTH = 10
    }
}

data class UiState(
    val inputText: String = "",
    val inputTextLength: String = "0",
    val buttonBackgroundRes: Int = core_design.drawable.bg_rect_gray200_r15,
    val buttonTextColor: Int = core_design.color.gray_450,
    val isGuideVisible: Boolean = false,
)
