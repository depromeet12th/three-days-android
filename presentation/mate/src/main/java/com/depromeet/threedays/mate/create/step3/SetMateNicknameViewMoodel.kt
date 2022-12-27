package com.depromeet.threedays.mate.create.step3

import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
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

    lateinit var habit: HabitUI

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

    fun setClickHabit(clickedHabit: HabitUI, mateType: String) {
        habit = clickedHabit
        _uiState.update {
            it.copy(
                boxImageResId = when(mateType) {
                    "Whipping" -> {
                        when(habit.color) {
                            Color.GREEN -> R.drawable.bg_box_mate_completion_whip_green
                            Color.BLUE -> R.drawable.bg_box_mate_completion_whip_blue
                            Color.PINK -> R.drawable.bg_box_mate_completion_whip_pink
                        }
                    }
                    else -> {
                        when(habit.color) {
                            Color.GREEN -> R.drawable.bg_box_mate_completion_carrot_green
                            Color.BLUE -> R.drawable.bg_box_mate_completion_carrot_blue
                            Color.PINK -> R.drawable.bg_box_mate_completion_carrot_pink
                        }
                    }
                }
            )
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
    val boxImageResId: Int = R.drawable.bg_box_mate_default,
)
