package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.entity.request.UpdateGoalRequest
import com.depromeet.threedays.domain.usecase.DeleteGoalUseCase
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
    private val deleteGoalUseCase: DeleteGoalUseCase,
) : BaseViewModel() {

    private val _goals: MutableStateFlow<List<Goal>> = MutableStateFlow(emptyList())
    val goals: StateFlow<List<Goal>>
        get() = _goals

    fun fetchGoals() {
        viewModelScope.launch {
            getAllGoalsUseCase().collect {
                _goals.value = it
            }
        }
    }

    fun updateGoals(updateGoalRequest: UpdateGoalRequest) {
        viewModelScope.launch {
            try {
                updateGoalUseCase(updateGoalRequest)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun deleteGoals(goalId: Long) {
        viewModelScope.launch {
            try {
                deleteGoalUseCase(goalId)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
