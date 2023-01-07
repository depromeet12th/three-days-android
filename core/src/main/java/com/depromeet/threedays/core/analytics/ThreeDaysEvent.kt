package com.depromeet.threedays.core.analytics

enum class ButtonType{
    Next,
    Save,
    NewHabit,
    Check
}

enum class Screen {
    CreateHabit,
    HomeDefault,
    HomeActivated,
    MateDefault,
    MateHome,
    MateClap,
    MateLevelup,
    MateCompleted
}

enum class ThreeDaysEvent {
    SplashViewed,
    OnboardingViewed,
    SignupViewed,
    ButtonClicked,
    SignupCompletedViewed,
    HomeDefaultViewed,
    HomeHabitClicked,
    HomeActivatedViewed,
    CheckClicked,
    MateOnboardingViewed,
    MateDefaultViewed,
    NewMateClicked,
    MateHomeViewed,
    MateShareClicked,
    MateClapOpenClicked,
    MateClapViewed,
    NewHabitClicked,
}
