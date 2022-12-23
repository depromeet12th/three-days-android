package com.depromeet.threedays.mypage.archived_habit

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.habit.DeleteArchivedHabitUseCase
import com.depromeet.threedays.domain.usecase.habit.GetArchivedHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchivedHabitViewModel @Inject constructor(
    private val getArchivedHabitsUseCase: GetArchivedHabitsUseCase,
    private val deleteArchivedHabitUseCase: DeleteArchivedHabitUseCase,
) : BaseViewModel() {
    private val _archivedHabits: MutableStateFlow<List<ArchivedHabitUI>> =
        MutableStateFlow(emptyList())
    val archivedHabits: StateFlow<List<ArchivedHabitUI>>
        get() = _archivedHabits

    private val _editable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val editable: StateFlow<Boolean>
        get() = _editable

    /**
     * 습관 보관함 > 보관한 습관 목록 조회
     */
    fun fetchArchivedHabits() {
        viewModelScope.launch {
            getArchivedHabitsUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        // Do nothing
                    }
                    Status.SUCCESS -> {
                        _archivedHabits.value = response.data!!.map { ArchivedHabitUI.from(it) }
                    }
                    Status.ERROR -> TODO()
                    Status.FAIL -> TODO()
                }
            }
        }
    }

    /**
     * 습관 보관함 > activity 수정 가능/불가능 상태 변경
     */
    fun toggleEditable() {
        viewModelScope.launch {
            val updated = !editable.value
            _editable.emit(updated)
            // 보관한 습관 수정가능/불가능 상태로 변경
            _archivedHabits.emit(
                _archivedHabits.value.map {
                    it.copyOf(
                        editable = updated,
                    )
                }
            )
        }
    }

    /**
     * 습관 보관함 > 보관한 습관 선택 여부 변경
     */
    fun toggleSelected(habitId: Long) {
        viewModelScope.launch {
            _archivedHabits.emit(
                _archivedHabits.value.map {
                    if (it.habitId == habitId) {
                        it.copyOf(selected = !it.selected)
                    } else {
                        it
                    }
                }
            )
        }
    }

    /**
     * 습관 보관함 > 함께했던 짝궁 열기
     */
    fun openMateUI(archivedHabitUI: ArchivedHabitUI) {
        viewModelScope.launch {
            _archivedHabits.emit(
                archivedHabits.value.map {
                    if (it.habitId == archivedHabitUI.habitId) {
                        it.copyOf(mateOpened = true)
                    } else {
                        it
                    }
                }
            )
        }
    }

    /**
     * 습관 보관함 > 함께했던 짝궁 닫기
     */
    fun closeMateUI(archivedHabitUI: ArchivedHabitUI) {
        viewModelScope.launch {
            _archivedHabits.emit(
                archivedHabits.value.map {
                    if (it.habitId == archivedHabitUI.habitId) {
                        it.copyOf(mateOpened = false)
                    } else {
                        it
                    }
                }
            )
        }
    }

    /**
     * 습관 보관함 > 삭제하기
     */
    fun deleteSelected() {
        viewModelScope.launch {
            archivedHabits.value
                .filter { it.selected }
                .map { deleteArchivedHabitUseCase.invoke(habitId = it.habitId).collect() }
            val updatedHabits  = archivedHabits.value.filter { !it.selected }
            _archivedHabits.emit(updatedHabits)
        }
    }
}
