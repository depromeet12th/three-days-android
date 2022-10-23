package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.usecase.CreateGoalUseCase
import com.depromeet.threedays.domain.usecase.GetAllGoalsUseCase
import com.depromeet.threedays.domain.usecase.UpdateGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllGoalsUseCase: GetAllGoalsUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val createGoalUseCase: CreateGoalUseCase
) : BaseViewModel() {

    private val _goals: MutableStateFlow<List<Goal>> = MutableStateFlow(emptyList())
    val goals: StateFlow<List<Goal>>
        get() = _goals

    fun fetchGoals() {
        viewModelScope.launch {
            try {
                _goals.value = getAllGoalsUseCase()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun createGoals(goal: Goal) {
        viewModelScope.launch {
            try {
                createGoalUseCase(goal)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun onGoalClick(goal: Goal, clickedIndex: Int) {
        if (goal.clapIndex == clickedIndex) {
            viewModelScope.launch {
                try {
                    goal.apply { clapChecked = !clapChecked }
                    updateGoalUseCase(goal)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }
}
