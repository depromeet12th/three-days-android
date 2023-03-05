package com.depromeet.threedays.mate

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.core.analytics.MixPanelEvent
import com.depromeet.threedays.core.analytics.Screen
import com.depromeet.threedays.core.analytics.ThreeDaysEvent
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.OnboardingType
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.repository.HabitRepository
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.domain.usecase.mate.DeleteMateUseCase
import com.depromeet.threedays.domain.usecase.mate.GetMatesUseCase
import com.depromeet.threedays.domain.usecase.max_level_mate.ReadMaxLevelMateUseCase
import com.depromeet.threedays.domain.usecase.max_level_mate.WriteMaxLevelMateUseCase
import com.depromeet.threedays.domain.usecase.onboarding.ReadOnboardingUseCase
import com.depromeet.threedays.domain.usecase.onboarding.WriteOnboardingUseCase
import com.depromeet.threedays.mate.MateImageResourceResolver.Companion.levelToIconResourceFunction
import com.depromeet.threedays.mate.MateImageResourceResolver.Companion.levelToLockResourceFunction
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.create.step1.model.toMateUI
import com.depromeet.threedays.mate.model.StampUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_design

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMatesUseCase: GetMatesUseCase,
    private val writeOnboardingUseCase: WriteOnboardingUseCase,
    private val readOnboardingUseCase: ReadOnboardingUseCase,
    private val deleteMateUseCase: DeleteMateUseCase,
    private val habitRepository: HabitRepository,
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase,
    private val readMaxLevelMateUseCase: ReadMaxLevelMateUseCase,
    private val writeMaxLevelMateUseCase: WriteMaxLevelMateUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    private val _uiEffect: MutableSharedFlow<UiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<UiEffect>
        get() = _uiEffect

    init {
        checkIsFirstVisitor()
        fetchHabits()
    }

    fun fetchMate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isMateInitialized = false) }

            getMatesUseCase().collect { response ->
                response.onSuccess { mates ->
                    val myMate = mates.find { it.status == "ACTIVE" }
                    _uiState.update {
                        it.copy(
                            mate = myMate?.toMateUI() ,
                            hasMate = myMate != null,
                            backgroundResColor = if(myMate == null) {
                                core_design.color.white
                            } else {
                                core_design.color.gray_100
                            },
                            stamps = getStampsFromMate(myMate?.toMateUI())
                        )
                    }
                    myMate?.let {
                        fetchHabit(it.habitId)
                    }
                    checkMateAchieveMaxLevel(uiState.value.mate)
                    
                    if (myMate == null) {
                          AnalyticsUtil.event(
                              name = ThreeDaysEvent.MateDefaultViewed.toString(),
                              properties = mapOf(
                                  MixPanelEvent.ScreenName to Screen.MateDefault.toString(),
                              )
                          )
                      } else {
                          AnalyticsUtil.event(
                              name = ThreeDaysEvent.MateHomeViewed.toString(),
                              properties = mapOf(
                                  MixPanelEvent.ScreenName to Screen.MateHome.toString(),
                              )
                          )
                      }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }

            _uiState.update { it.copy(isMateInitialized = true) }
        }
    }

    private fun fetchHabits() {
        viewModelScope.launch {
            _uiState.update { it.copy(isHabitListInitialized = false) }

            getActiveHabitsUseCase().collect { response ->
                response.onSuccess { habitList ->
                    _uiState.update {
                        it.copy(
                            hasHabit = habitList.isNotEmpty()
                        )
                    }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    sendErrorMessage(throwable.message)
                }
            }

            _uiState.update { it.copy(isHabitListInitialized = true) }
        }
    }

    private fun fetchHabit(habitId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isHabitInitialized = false) }

            habitRepository.getHabit(habitId = habitId)
                .onSuccess { habit ->
                    _uiState.update {
                        it.copy(
                            habit = habit
                        )
                    }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException
                    sendErrorMessage(throwable.message)
                }

            _uiState.update { it.copy(isHabitInitialized = true) }
        }
    }

    private fun checkIsFirstVisitor() {
        viewModelScope.launch {
            val response = readOnboardingUseCase.execute(OnboardingType.MATE)
            if (response == null) {
                _uiEffect.emit(
                    UiEffect.ShowMateOnboarding
                )
            }
        }
    }

    fun writeIsFirstVisitor() {
        viewModelScope.launch {
            writeOnboardingUseCase.execute(OnboardingType.MATE)
        }
    }

    fun deleteMate() {
        uiState.value.mate?.let {
            viewModelScope.launch {
                deleteMateUseCase(
                    habitId = it.habitId,
                    mateId = it.id,
                ).collect { response ->
                    response.onSuccess {
                        fetchMate()
                        _uiEffect.emit(
                            value = UiEffect.ShowToastMessage(R.string.delete_mate)
                        )
                    }.onFailure { throwable ->
                        // TODO: 받아오는 값이 null인데 타입이 안맞아서 에러뜨고 있음. 요청은 정상적으로 잘 돼서 임시로 ㅠㅠ
//                        fetchMate()
//                        _uiEffect.emit(
//                            value = UiEffect.ShowToastMessage(R.string.delete_mate)
//                        )
                        throwable as ThreeDaysException
                        sendErrorMessage(throwable.message)
                    }
                }
            }
        }
    }

    private fun getStampsFromMate(mate: MateUI?): MutableList<StampUI> {
        val stamps = mutableListOf<StampUI>()

        if(mate?.levelUpSectioin != null) {
            val maxLevel = mate.levelUpSectioin.last()
            for (stampCount in 1..maxLevel) {
                if (mate.levelUpSectioin.contains(stampCount)) {
                    stamps.add(getCharacterStamp(stampCount, mate.levelUpSectioin.indexOf(stampCount), mate.level))
                } else if (stampCount <= (mate.reward ?: 0)) {
                    stamps.add(getColorStamp(stampCount))
                } else {
                    stamps.add(StampUI(stampCount = stampCount.toLong()))
                }
            }
        }


        return stamps
    }

    private fun getCharacterStamp(stampCount: Int, mateLevel: Int, myMateLevel: Int): StampUI {
        return StampUI(
            stampCount = stampCount.toLong(),
            backgroundResId = if(myMateLevel >= mateLevel) {
                levelToIconResourceFunction(mateLevel)
            } else {
                levelToLockResourceFunction(mateLevel + 1)
            }
        )
    }

    private fun getColorStamp(stampCount: Int): StampUI {
        return StampUI(
            stampCount = stampCount.toLong(),
            backgroundResId = when(uiState.value.habit?.color) {
                Color.GREEN -> R.drawable.bg_oval_hand_green
                Color.PINK -> R.drawable.bg_oval_hand_pink
                else -> R.drawable.bg_oval_hand_blue
            }
        )
    }

    private fun checkMateAchieveMaxLevel(mate: MateUI?) {
        mate?.let {
            if((mate.levelUpSectioin?.last() ?: MATE_MAX_CLAP) == mate.reward) {

                viewModelScope.launch {
                    val isShown = readMaxLevelMateUseCase.execute("${KEY_MAX_LEVEL_DIALOG_SHOWN}:${mate.id}")

                    if(isShown == null) {
                        _uiEffect.emit(
                            UiEffect.ShowAchieveMaxLevel(
                                mateLevel = mate.level
                            )
                        )
                        writeMaxLevelMateUseCase.execute("${KEY_MAX_LEVEL_DIALOG_SHOWN}:${mate.id}", "TRUE")
                    }
                }
            }
        }
    }

    companion object {
        private const val MATE_MAX_CLAP = 22
        private const val KEY_MAX_LEVEL_DIALOG_SHOWN = "KEY_MAX_LEVEL_DIALOG_SHOWN"
    }
}

data class UiState(
    val mate: MateUI? = null,
    val habit: SingleHabit? = null,
    val hasHabit: Boolean = false,
    val hasMate: Boolean = false,
    val backgroundResColor: Int = core_design.color.gray_100,
    val stamps: List<StampUI> = emptyList(),
    val isMateInitialized: Boolean = false,
    val isHabitInitialized: Boolean = false,
    val isHabitListInitialized: Boolean = false
)

sealed interface UiEffect {
    data class ShowToastMessage(val resId: Int) : UiEffect
    data class ShowAchieveMaxLevel(
        val mateLevel: Int
    ) : UiEffect
    object ShowMateOnboarding : UiEffect
}
