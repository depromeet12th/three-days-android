package com.depromeet.threedays.mate.share

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.core_design_system.R
import com.depromeet.threedays.domain.entity.Color
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareMateViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun fetchMate(habitId: Long) {
        if(habitId != INVALID_HABIT_ID) {
            viewModelScope.launch {
                kotlin.runCatching {
                    habitRepository.getHabit(habitId = habitId)
                }.onSuccess { singleHabit ->
                    _uiState.update {
                        it.copy(
                            singleHabit = singleHabit,
                            backgroundResId = getBackgroundResIdByColor(singleHabit.color)
                        )
                    }
                }.onFailure { throwable ->
                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    private fun getBackgroundResIdByColor(color: Color): Int {
        return when(color) {
            Color.GREEN -> R.drawable.bg_rect_green50_r10
            Color.BLUE -> R.drawable.bg_rect_blue50_r10
            Color.PINK ->R.drawable.bg_rect_pink50_r10
        }
    }

    companion object {
        const val INVALID_HABIT_ID = -1L
    }
}

data class UiState(
    val singleHabit: SingleHabit? = null,
    val backgroundResId: Int = R.drawable.bg_rect_green50_r10,
)
