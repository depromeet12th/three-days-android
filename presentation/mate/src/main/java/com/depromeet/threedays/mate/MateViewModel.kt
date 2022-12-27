package com.depromeet.threedays.mate

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.mate.GetMatesUseCase
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.create.step1.model.toMateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.depromeet.threedays.core_design_system.R as core_desin
import javax.inject.Inject

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMatesUseCase: GetMatesUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        fetchMate()
    }

    private fun fetchMate() {
        viewModelScope.launch {
            getMatesUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val myMate = response.data!!.find { it.status == "ACTIVE" }
                        _uiState.update {
                            it.copy(
                                mate = myMate?.toMateUI() ,
                                hasMate = myMate != null,
                                backgroundResColor = if(myMate == null) {
                                    core_desin.color.white
                                } else {
                                    core_desin.color.gray_100
                                }
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
    val backgroundResColor: Int = core_desin.color.gray_100,
)
