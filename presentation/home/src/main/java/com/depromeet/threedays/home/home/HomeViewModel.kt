package com.depromeet.threedays.home.home

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.OnboardingType
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.DeleteHabitUseCase
import com.depromeet.threedays.domain.usecase.achievement.CreateHabitAchievementUseCase
import com.depromeet.threedays.domain.usecase.achievement.DeleteHabitAchievementUseCase
import com.depromeet.threedays.domain.usecase.habit.GetActiveHabitsUseCase
import com.depromeet.threedays.domain.usecase.onboarding.ReadOnboardingUseCase
import com.depromeet.threedays.domain.usecase.onboarding.WriteOnboardingUseCase
import com.depromeet.threedays.home.R
import com.depromeet.threedays.home.home.model.HabitUI
import com.depromeet.threedays.home.home.model.toHabitUI
import com.depromeet.threedays.mate.MateImageResourceResolver.Companion.levelToResourceFunction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val readOnboardingUseCase: ReadOnboardingUseCase,
    private val writeOnboardingUseCase: WriteOnboardingUseCase,
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase,
    private val createHabitAchievementUseCase: CreateHabitAchievementUseCase,
    private val deleteHabitAchievementUseCase: DeleteHabitAchievementUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
) : BaseViewModel() {

    private val _habits: MutableStateFlow<List<HabitUI>> = MutableStateFlow(emptyList())
    val habits: StateFlow<List<HabitUI>>
        get() = _habits

    private val _uiEffect: MutableSharedFlow<UiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<UiEffect>
        get() = _uiEffect

    init {
        fetchGoals()
        checkIsFirstVisitor()
    }

    private fun checkIsFirstVisitor() {
        viewModelScope.launch {
            val response = readOnboardingUseCase.execute(OnboardingType.NOTIFICATION_RECOMMEND)
            if(response == null) {
                _uiEffect.emit(
                    UiEffect.ShowNotiRecommendBottomSheet
                )
            } else {
                _uiEffect.emit(
                    UiEffect.ShowNotiGuideBottomSheet
                )
            }

            writeOnboardingUseCase.execute(OnboardingType.NOTIFICATION_RECOMMEND)
        }
    }

    fun fetchGoals() {
        viewModelScope.launch {
            getActiveHabitsUseCase().collect { response ->
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

    fun createHabitAchievement(habitUI: HabitUI) {
        viewModelScope.launch {
            createHabitAchievementUseCase(habitUI.habitId).collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        fetchGoals()
                        checkNewClap(habitUI)
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

    fun deleteHabitAchievement(habitId: Long, habitAchievementId: Long) {
        viewModelScope.launch {
            deleteHabitAchievementUseCase(
                habitId = habitId,
                habitAchievementId = habitAchievementId,
            ).collect { response ->
                when(response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        fetchGoals()
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

    fun deleteHabit(habitUI: HabitUI) {
        if(habitUI.mate != null) {
            deleteConnectedMateHabitType(habitUI.habitId)
        } else if(habitUI.sequence > 0) {
            deleteHasAchievementHabitType(habitUI.habitId)
        } else {
            deleteNoAchievementHabitType(habitUI.habitId)
        }
    }

    private fun deleteConnectedMateHabitType(habitId: Long) {
        viewModelScope.launch {
            _uiEffect.emit(
                UiEffect.ShowDeleteHabitDialog(
                    habitId = habitId,
                    habitType = HabitType.ConnectedMate,
                    titleResId = R.string.delete_dialog_title_connected_mate,
                    descriptionResId = R.string.delete_dialog_description_connected_mate,
                )
            )
        }
    }

    private fun deleteHasAchievementHabitType(habitId: Long) {
        viewModelScope.launch {
            _uiEffect.emit(
                UiEffect.ShowDeleteHabitDialog(
                    habitId = habitId,
                    habitType = HabitType.HasAchievement,
                    titleResId = R.string.delete_dialog_title,
                    descriptionResId = R.string.delete_dialog_description_has_achievement,
                )
            )
        }
    }

    private fun deleteNoAchievementHabitType(habitId: Long) {
        viewModelScope.launch {
            _uiEffect.emit(
                UiEffect.ShowDeleteHabitDialog(
                    habitId = habitId,
                    habitType = HabitType.NoAchievement,
                    titleResId = R.string.delete_dialog_title,
                )
            )
        }
    }

    fun onDeleteHabitClick(habitType: HabitType, habitId: Long?) {
        if(habitId == null) {
            // TODO: show error dialog
            return
        }
        
        viewModelScope.launch {
            deleteHabitUseCase(habitId).collect { response ->
                when (response.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        onSuccessDeleteHabit(habitType)
                        fetchGoals()
                    }
                    Status.ERROR -> {

                    }
                    Status.FAIL -> {

                    }
                }
            }
        }
    }

    private fun onSuccessDeleteHabit(habitType: HabitType) {
        viewModelScope.launch {
            when(habitType) {
                HabitType.ConnectedMate -> {
                    _uiEffect.emit(
                        UiEffect.ShowSnackBar(
                            textResId = R.string.snack_bar_text_connected_mate,
                            actionTextResId = R.string.move,
                        )
                    )
                }
                HabitType.HasAchievement -> {
                    _uiEffect.emit(
                        UiEffect.ShowSnackBar(
                            textResId = R.string.snack_bar_text_has_achievement,
                            actionTextResId = R.string.move,
                        )
                    )
                }
                HabitType.NoAchievement -> {
                    _uiEffect.emit(
                        UiEffect.ShowToastMessage(
                            resId = R.string.habit_delete_success_message,
                        )
                    )
                }
            }
        }
    }

    private fun checkNewClap(habitUI: HabitUI) {
        val hasNewClap = habitUI.todayIndex == 2 && habitUI.sequence == 2 && !habitUI.isTodayChecked
        if(hasNewClap) {
            viewModelScope.launch {
                _uiEffect.emit(
                    UiEffect.ShowClapAnimation(habitUI.habitId)
                )
            }
        }
    }

    fun checkLevelUpHabit(habitId: Long) {
        val habitWithMate = habits.value.find { it.mate != null && it.todayIndex == 0 && it.sequence > 0 && it.isTodayChecked } ?: return
        if(habitWithMate.habitId != habitId) return
        val mate = habitWithMate.mate ?: return
        val levelUpAt = mate.levelUpAt ?: return
        val today = LocalDate.now().toString()

        if(today == levelUpAt.toLocalDate().toString()) {
            viewModelScope.launch {
                _uiEffect.emit(
                    UiEffect.ShowImageSnackBar(
                        imageResId = levelToResourceFunction(mate.level),
                        titleResId = R.string.level_up_title,
                        contentResId = R.string.level_up_content,
                        actionTextResId = R.string.move,
                        mateLevel = mate.level,
                    )
                )
            }
        }
    }
}

sealed interface UiEffect {
    data class ShowToastMessage(val resId: Int): UiEffect
    data class ShowDeleteHabitDialog(
        val habitType: HabitType,
        val habitId: Long? = null,
        val titleResId: Int,
        val descriptionResId: Int? = null,
        val cancelTextResId: Int = R.string.delete_dialog_cancel_text,
        val confirmTextResId: Int = R.string.delete_dialog_confirm_text,
        val buttonTopMargin: Float = 30f,
    ): UiEffect
    data class ShowSnackBar(
        val textResId: Int,
        val actionTextResId: Int,
    ): UiEffect
    data class ShowImageSnackBar(
        val imageResId: Int,
        val titleResId: Int,
        val contentResId: Int,
        val actionTextResId: Int,
        val mateLevel: Int,
    ) : UiEffect
    data class ShowClapAnimation(
        val habitId: Long,
    ) : UiEffect
    object ShowNotiGuideBottomSheet : UiEffect
    object ShowNotiRecommendBottomSheet : UiEffect
}

sealed interface HabitType {
    object ConnectedMate: HabitType
    object HasAchievement: HabitType
    object NoAchievement: HabitType
}
