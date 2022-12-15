package com.depromeet.threedays.mypage.archived_habit

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.GetArchivedHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchivedHabitViewModel @Inject constructor(
    private val getArchivedHabitsUseCase: GetArchivedHabitsUseCase,
) : BaseViewModel() {
    private val _archivedHabits: MutableStateFlow<List<ArchivedHabitUI>> =
        MutableStateFlow(emptyList())
    val archivedHabits: StateFlow<List<ArchivedHabitUI>>
        get() = _archivedHabits

    fun fetchArchivedHabits() {
        viewModelScope.launch {
            getArchivedHabitsUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        _archivedHabits.value = response.data!!.map { ArchivedHabitUI.from(it) }
                    }
                    Status.ERROR -> TODO()
                    Status.FAIL -> TODO()
                }
            }
        }
    }
}
