package com.depromeet.threedays.history

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.domain.usecase.record.GetRecordUseCase
import com.depromeet.threedays.domain.util.GetStringFromDateTime
import com.depromeet.threedays.history.model.HabitUI
import com.depromeet.threedays.history.model.toHabitUI
import com.depromeet.threedays.history.model.toPresentationModel
import com.depromeet.threedays.core_design_system.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase,
    private val getRecordUseCase: GetRecordUseCase,
    private val getStringFromDateTime: GetStringFromDateTime,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        fetchHabits()
        fetchRecord()
    }

    private fun fetchHabits() {
        viewModelScope.launch {
            getActiveHabitsUseCase().collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                habits = response.data!!.map { it.toHabitUI() }
                            )
                        }
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

    private fun fetchRecord() {
        val firstDayOfMonth = uiState.value.thisMonth.run {
            LocalDate.of(year, month, 1)
        }
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)
        val from = getStringFromDateTime(firstDayOfMonth)
        val to = getStringFromDateTime(lastDayOfMonth)

        viewModelScope.launch {
            getRecordUseCase(to = to, from = from).collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val recordUI = response.data!!.toPresentationModel()
                        _uiState.update {
                            it.copy(
                                rewardCount = recordUI.rewardCount.toString(),
                                achievementCount = recordUI.achievementCount.toString(),
                                emoji = recordUI.frequentHabitUI?.imojiPath ?: String.Empty,
                                title = recordUI.frequentHabitUI?.title ?: String.Empty,
                                cardBackgroundResId = getCardBackgroundResId(
                                    recordUI.frequentHabitUI?.color ?: "GREEN"
                                )
                            )
                        }
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
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
        _uiState.update {
            it.copy(
                thisMonth = it.thisMonth.minusMonths(1)
            )
        }
        fetchRecord()
    }

    fun onClickNextMonth() {
        _uiState.update {
            it.copy(
                thisMonth = it.thisMonth.plusMonths(1)
            )
        }
        fetchRecord()
    }
}

data class UiState(
    val habits: List<HabitUI> = emptyList(),
    val thisMonth: LocalDate = LocalDate.now(),

    val rewardCount: String = String.Empty,
    val achievementCount: String = String.Empty,
    val emoji: String = String.Empty,
    val title: String  = String.Empty,
    val cardBackgroundResId: Int = R.drawable.bg_rect_green50_r10,
)
