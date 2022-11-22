package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.usecase.GetHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
//    private val updateHabitUseCase: UpdateHabitUseCase,
//    private val deleteHabitUseCase: DeleteHabitUseCase,
) : BaseViewModel() {

    private val _habits: MutableStateFlow<List<Habit>> = MutableStateFlow(emptyList())
    val habits: StateFlow<List<Habit>>
        get() = _habits

    fun fetchGoals() {
        viewModelScope.launch {
            getHabitsUseCase().collect {
                when(it.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _habits.value = it.data!!
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

//    fun updateGoals(updateGoalRequest: UpdateGoalRequest) {
//        viewModelScope.launch {
//            try {
//                updateHabitUseCase(updateGoalRequest)
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//            }
//        }
//    }
//
//    fun deleteGoals(goalId: Long) {
//        viewModelScope.launch {
//            try {
//                deleteHabitUseCase(goalId)
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//            }
//        }
//    }
}
