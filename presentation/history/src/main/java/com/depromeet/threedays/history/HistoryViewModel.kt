package com.depromeet.threedays.history

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core_design_system.R
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.domain.usecase.record.GetRecordUseCase
import com.depromeet.threedays.domain.util.GetDateTimeFromString
import com.depromeet.threedays.domain.util.GetStringFromDateTime
import com.depromeet.threedays.history.model.HabitUI
import com.depromeet.threedays.history.model.toHabitUI
import com.depromeet.threedays.history.model.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase,
    private val getRecordUseCase: GetRecordUseCase,
    private val getStringFromDateTime: GetStringFromDateTime,
    private val getDateTimeFromString: GetDateTimeFromString,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun fetchHabits() {
        viewModelScope.launch {
            getActiveHabitsUseCase().collect { response ->
                response.onSuccess { habits ->
                    _uiState.update {
                        it.copy(isHabitInitialized = true)
                    }

                    if(habits.isNotEmpty()) {
                        val sortedHabits = habits.sortedBy { it.createAt }
                        val startDate = sortedHabits.first()
                        val endDate = sortedHabits.last()

                        _uiState.update { uiState ->
                            uiState.copy(
                                habits = habits.map { it.toHabitUI() },
                                startDate = getDateTimeFromString(startDate.createAt) ?: uiState.startDate,
                                endDate = getDateTimeFromString(endDate.createAt) ?: uiState.endDate,
                            )
                        }
                        _uiState.update {
                            it.copy(
                                previousMonthClickable = canMoveToPreviousMonth(),
                                nextMonthClickable = canMoveToNextMonth(),
                            )
                        }
                    }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    Timber.e("--- HomeViewModel code: ${throwable.code}, message: ${throwable.message}")
                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    fun fetchRecord() {
        val firstDayOfMonth = getYearMonthWithFirstDay(uiState.value.thisMonth)
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)
        val from = getStringFromDateTime(firstDayOfMonth)
        val to = getStringFromDateTime(lastDayOfMonth)

        viewModelScope.launch {
            getRecordUseCase(to = to, from = from).collect { response ->
                response.onSuccess { record ->
                    val recordUI = record.toPresentationModel()
                    _uiState.update {
                        it.copy(
                            rewardCount = recordUI.rewardCount.toString(),
                            achievementCount = recordUI.achievementCount.toString(),
                            emoji = recordUI.frequentHabitUI?.imojiPath ?: String.Empty,
                            title = recordUI.frequentHabitUI?.title ?: String.Empty,
                            cardBackgroundResId = getCardBackgroundResId(
                                recordUI.frequentHabitUI?.color ?: "GREEN"
                            ),
                            isRecordInitialized = true
                        )
                    }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    Timber.e("--- HomeViewModel code: ${throwable.code}, message: ${throwable.message}")
                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    private fun getCardBackgroundResId(color: String): Int {
        return when(color) {
            "GREEN" -> R.drawable.bg_rect_green50_r10
            "PINK" -> R.drawable.bg_rect_pink50_r10
            else -> R.drawable.bg_rect_blue50_r10
        }
    }

    fun onClickPrevMonth() {
        if(canMoveToPreviousMonth()) {
            _uiState.update {
                it.copy(
                    thisMonth = it.thisMonth.minusMonths(1),
                )
            }
            fetchRecord()
        }

        _uiState.update {
            it.copy(
                previousMonthClickable = canMoveToPreviousMonth(),
                nextMonthClickable = canMoveToNextMonth(),
            )
        }
    }

    fun onClickNextMonth() {
        if(canMoveToNextMonth()) {
            _uiState.update {
                it.copy(
                    thisMonth = it.thisMonth.plusMonths(1),
                )
            }
            fetchRecord()
        }

        _uiState.update {
            it.copy(
                previousMonthClickable = canMoveToPreviousMonth(),
                nextMonthClickable = canMoveToNextMonth(),
            )
        }
    }

    private fun canMoveToPreviousMonth(): Boolean {
        val state = uiState.value
        return (getYearMonthWithFirstDay(state.thisMonth)) > getYearMonthWithFirstDay(state.startDate)
    }

    private fun canMoveToNextMonth(): Boolean {
        val state = uiState.value
        return (getYearMonthWithFirstDay(state.thisMonth)) < getYearMonthWithFirstDay(state.endDate)
    }

    private fun getYearMonthWithFirstDay(date: LocalDate): LocalDate {
        date.apply {
            return LocalDate.of(year, month, 1)
        }
    }
}

data class UiState(
    val habits: List<HabitUI> = emptyList(),
    val thisMonth: LocalDate = LocalDate.now(),
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val previousMonthClickable: Boolean = true,
    val nextMonthClickable: Boolean = true,

    val rewardCount: String = String.Empty,
    val achievementCount: String = String.Empty,
    val emoji: String = String.Empty,
    val title: String  = String.Empty,
    val cardBackgroundResId: Int = R.drawable.bg_rect_green50_r10,

    val isHabitInitialized: Boolean = false,
    val isRecordInitialized: Boolean = false
)
