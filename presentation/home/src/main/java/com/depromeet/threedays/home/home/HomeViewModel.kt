package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.DeleteHabitUseCase
import com.depromeet.threedays.domain.usecase.GetHabitsUseCase
import com.depromeet.threedays.home.home.model.HabitUI
import com.depromeet.threedays.home.home.model.toHabitUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
//    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
) : BaseViewModel() {

    private val _habits: MutableStateFlow<List<HabitUI>> = MutableStateFlow(emptyList())
    val habits: StateFlow<List<HabitUI>>
        get() = _habits

    private val _uiEffect: MutableSharedFlow<UiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<UiEffect>
        get() = _uiEffect

    fun fetchGoals() {
        viewModelScope.launch {
            getHabitsUseCase().collect { response ->
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
    fun deleteGoals(habitId: Long) {
        viewModelScope.launch {
            deleteHabitUseCase(habitId).collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _uiEffect.emit(
                            UiEffect.DeleteDialog
                        )
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

    sealed interface UiEffect {
        object DeleteDialog: UiEffect
    }
}
