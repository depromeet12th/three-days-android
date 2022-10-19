package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.domain.usecase.GetAllGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllGoalsUseCase: GetAllGoalsUseCase
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
}
