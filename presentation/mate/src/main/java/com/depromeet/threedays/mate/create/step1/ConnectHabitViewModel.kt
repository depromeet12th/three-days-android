package com.depromeet.threedays.mate.create.step1

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.create.step1.model.toHabitUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectHabitViewModel @Inject constructor(
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase
) : BaseViewModel() {

    private val _habits: MutableStateFlow<List<HabitUI>> = MutableStateFlow(emptyList())
    val habits: StateFlow<List<HabitUI>>
        get() = _habits

    init {
        fetchHabits()
    }

    private fun fetchHabits() {
        viewModelScope.launch {
            getActiveHabitsUseCase().collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _habits.value = response.data!!.map { it.toHabitUI() }
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }
}
