package com.depromeet.threedays.mate

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.mate.DeleteMateUseCase
import com.depromeet.threedays.domain.usecase.mate.GetMatesUseCase
import com.depromeet.threedays.domain.usecase.onboarding.ReadOnboardingUseCase
import com.depromeet.threedays.domain.usecase.onboarding.WriteOnboardingUseCase
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.create.step1.model.toMateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_desin

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMatesUseCase: GetMatesUseCase,
    private val writeOnboardingUseCase: WriteOnboardingUseCase,
    private val readOnboardingUseCase: ReadOnboardingUseCase,
    private val deleteMateUseCase: DeleteMateUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    private val _uiEffect: MutableSharedFlow<UiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<UiEffect>
        get() = _uiEffect


    init {
        checkIsFirstVisitor()
    }

    fun fetchMate() {
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

    fun deleteMate() {
        uiState.value.mate?.let {
            viewModelScope.launch {
                deleteMateUseCase(
                    habitId = it.habitId,
                    mateId = it.id,
                ).collect { response ->
                    when (response.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            fetchMate()
                            _uiEffect.emit(
                                value = UiEffect.ShowToastMessage(R.string.delete_mate)
                            )
                        }
                        // TODO: 받아오는 값이 null인데 타입이 안맞아서 에러뜨고 있음. 요청은 정상적으로 잘 돼서 임시로 ㅠㅠ
                        Status.ERROR -> {
                            fetchMate()
                            _uiEffect.emit(
                                value = UiEffect.ShowToastMessage(R.string.delete_mate)
                            )
                        }
                        Status.FAIL -> {

                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val IS_FIRST_VISIT_ONBOARDING_MATE = "IS_FIRST_VISIT_ONBOARDING_MATE"
    }
}

data class UiState(
    val mate: MateUI? = null,
    val hasMate: Boolean = false,
    val backgroundResColor: Int = core_desin.color.gray_100,
    val isFirstVisitor: Boolean = false,
)

sealed interface UiEffect {
    data class ShowToastMessage(val resId: Int) : UiEffect
}
