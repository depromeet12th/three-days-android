package com.depromeet.threedays.mate.create.step1

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.create.step1.model.toHabitUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectHabitViewModel @Inject constructor(
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase
) : BaseViewModel() {

    private val _habits: MutableStateFlow<List<HabitUI>> = MutableStateFlow(emptyList())
    val habits: StateFlow<List<HabitUI>>
        get() = _habits

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        fetchHabits()
    }

    private fun fetchHabits() {
        viewModelScope.launch {
            getActiveHabitsUseCase().collect { response ->
                response.onSuccess {
                    _habits.value = it.map { habit -> habit.toHabitUI() }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    fun setHabitClickStatus(clickedHabit: HabitUI) {
        _uiState.update {
            it.copy(
                clickedHabit = clickedHabit,
                boxImageResId = when(clickedHabit.color) {
                    Color.PINK -> R.drawable.bg_box_mate_default_pink
                    Color.BLUE -> R.drawable.bg_box_mate_default_blue
                    Color.GREEN -> R.drawable.bg_box_mate_default_green
                },
                buttonClickable = true,
                buttonBackgroundRes = com.depromeet.threedays.core_design_system.R.drawable.bg_rect_gray800_r15,
                buttonTextColor = com.depromeet.threedays.core_design_system.R.color.white
            )
        }
    }
}

data class UiState(
    val clickedHabit: HabitUI? = null,
    val boxImageResId: Int = R.drawable.bg_box_mate_default,
    val buttonClickable: Boolean = false,
    val buttonBackgroundRes: Int = com.depromeet.threedays.core_design_system.R.drawable.bg_rect_gray200_r15,
    val buttonTextColor: Int = com.depromeet.threedays.core_design_system.R.color.gray_450,
)
