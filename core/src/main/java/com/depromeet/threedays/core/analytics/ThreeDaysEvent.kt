package com.depromeet.threedays.core.analytics

enum class ButtonType{
    Next,
    Save,
    NewHabit,
    Check,
    NewMate,
    Share,
    SaveImg,
    Insta,
    Close,
    MateClapOpen,
    Carrot,
    Sparta,
    MateSave,
}

enum class Screen {
    CreateHabit,
    HomeDefault,
    HomeActivated,
    MateDefault,
    MateHome,
    MateClap,
    MateLevelup,
    MateCompleted,
    MateShare,
    MateMaking,
    Push
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
    SharedPathClicked,
    MateMakingViewed,
    MateSelected,
    MateLevelupViewed,
    MateCompletedViewed,
    MateSaveClicked,
    PushViewed
}
