package com.depromeet.threedays.mate.create.step2

import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.core_design_system.R as core_design
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChooseMateTypeViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    lateinit var habit: HabitUI

    fun setMateType(mateType: MateType) {
        when(mateType) {
            MateType.WhippingMate -> {
                _uiState.update {
                    it.copy(
                        mateType = MateType.WhippingMate,
                        whippingMateBackgroundRes = R.drawable.bg_rect_gray_200_border_gray_400_r10,
                        carrotMateBackgroundRes = core_design.drawable.bg_rect_gray100_r10,
                        whippingMateRes = R.drawable.ic_whipping_mate_selected,
                        carrotMateRes = R.drawable.ic_carrot_mate_unselected,
                        boxImageResId = when(habit.color) {
                            Color.GREEN -> R.drawable.bg_box_mate_whip_green
                            Color.BLUE -> R.drawable.bg_box_mate_whip_blue
                            Color.PINK -> R.drawable.bg_box_mate_whip_pink
                        }
                    )
                }
            }
            MateType.CarrotMate -> {
                _uiState.update {
                    it.copy(
                        mateType = MateType.CarrotMate,
                        whippingMateBackgroundRes = core_design.drawable.bg_rect_gray100_r10,
                        carrotMateBackgroundRes = R.drawable.bg_rect_gray_200_border_gray_400_r10,
                        whippingMateRes = R.drawable.ic_whipping_mate_unselected,
                        carrotMateRes = R.drawable.ic_carrot_mate_selected,
                        boxImageResId = when(habit.color) {
                            Color.GREEN -> R.drawable.bg_box_mate_carrot_green
                            Color.BLUE -> R.drawable.bg_box_mate_carrot_blue
                            Color.PINK -> R.drawable.bg_box_mate_carrot_pink
                        }
                    )
                }
            }
        }
    }

    fun setClickHabit(clickedHabit: HabitUI) {
        habit = clickedHabit
        _uiState.update {
            it.copy(
                boxImageResId = when(habit.color) {
                    Color.GREEN -> R.drawable.bg_box_mate_whip_green
                    Color.BLUE -> R.drawable.bg_box_mate_whip_blue
                    Color.PINK -> R.drawable.bg_box_mate_whip_pink
                }
            )
        }
    }
}

data class UiState(
    val mateType: MateType = MateType.WhippingMate,
    val whippingMateBackgroundRes: Int = R.drawable.bg_rect_gray_200_border_gray_400_r10,
    val carrotMateBackgroundRes: Int = core_design.drawable.bg_rect_gray200_r10,
    val whippingMateRes: Int = R.drawable.ic_whipping_mate_selected,
    val carrotMateRes: Int = R.drawable.ic_carrot_mate_unselected,
    val boxImageResId: Int = R.drawable.bg_box_mate_default,
)

sealed interface MateType {
    object WhippingMate : MateType
    object CarrotMate : MateType
}
