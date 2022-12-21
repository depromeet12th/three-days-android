package com.depromeet.threedays.mate

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.mate.GetMateUseCase
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.create.step1.model.toMateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MateViewModel @Inject constructor(
    val getMateUseCase: GetMateUseCase
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        fetchMate()
    }

    private fun fetchMate() {
        viewModelScope.launch {
            getMateUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                mate = response.data!!.toMateUI(),
                                hasMate = true,
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
}

data class UiState(
    val mate: MateUI? = null,
    val hasMate: Boolean = false,
)
