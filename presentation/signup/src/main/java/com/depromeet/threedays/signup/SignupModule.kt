package com.depromeet.threedays.signup

import com.depromeet.threedays.navigator.SignupNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SignupModule {
    @Binds
    abstract fun bindSignupNavigator(navigator: SignupNavigatorImpl): SignupNavigator
}
