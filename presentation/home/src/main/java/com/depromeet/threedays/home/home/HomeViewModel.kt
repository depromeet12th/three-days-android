package com.depromeet.threedays.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.usecase.CreateGoalUseCase
import com.depromeet.threedays.domain.usecase.GetAllGoalsUseCase
import com.depromeet.threedays.domain.usecase.UpdateGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllGoalsUseCase: GetAllGoalsUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val createGoalUseCase: CreateGoalUseCase
) : BaseViewModel() {

    private val _goals: MutableLiveData<List<Goal>> = MutableLiveData(emptyList())
    val goals: LiveData<List<Goal>>
        get() = _goals

    fun fetchGoals() {
        viewModelScope.launch {
            val response = getAllGoalsUseCase()
            if (response != null) {
                _goals.postValue(response)
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

    fun updateGoals(goal: Goal) {
        viewModelScope.launch {
            try {
                updateGoalUseCase(goal)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
