package com.depromeet.threedays.mate.create.step2

import com.depromeet.threedays.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseMateTypeViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun setMateType(whipping: MateType) {
        _uiState.update {
            it.copy(
                mateType = whipping
            )
        }
    }

    data class UiState(
        val mateType: MateType = MateType.WHIPPING
    )

    enum class MateType {
        WHIPPING, CARROT
    }
}
