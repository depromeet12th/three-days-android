package com.depromeet.threedays.mate

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.mate.GetMatesUseCase
import com.depromeet.threedays.domain.usecase.onboarding.ReadOnboardingUseCase
import com.depromeet.threedays.domain.usecase.onboarding.WriteOnboardingUseCase
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
    private val getMatesUseCase: GetMatesUseCase,
    private val writeOnboardingUseCase: WriteOnboardingUseCase,
    private val readOnboardingUseCase: ReadOnboardingUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        fetchMate()
        checkIsFirstVisitor()
    }

    private fun fetchMate() {
        viewModelScope.launch {
            getMatesUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                mate = response.data!!.find { it.status == "ACTIVE" }?.toMateUI() ,
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

    private fun checkIsFirstVisitor() {
        viewModelScope.launch {
            if(uiState.value.isFirstVisitor.not()) {
                val response = readOnboardingUseCase.execute(IS_FIRST_VISIT_ONBOARDING_MATE)
                _uiState.update {
                    it.copy(
                        isFirstVisitor = response == null || response == "true"
                    )
                }
            }
        }
    }

    fun writeIsFirstVisitor() {
        viewModelScope.launch {
            writeOnboardingUseCase.execute(IS_FIRST_VISIT_ONBOARDING_MATE, "false")
        }
    }

    companion object {
        private const val IS_FIRST_VISIT_ONBOARDING_MATE = "IS_FIRST_VISIT_ONBOARDING_MATE"
    }
}

data class UiState(
    val mate: MateUI? = null,
    val hasMate: Boolean = false,
    val isFirstVisitor: Boolean = false,
)
