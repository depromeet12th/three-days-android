package com.depromeet.threedays.onboarding

import com.depromeet.threedays.navigator.OnboardingNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OnboardingModule {

    @Binds
    abstract fun bindOnboardingNavigator(navi: OnboardingNavigatorImpl): OnboardingNavigator
}
