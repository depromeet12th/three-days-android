package com.depromeet.threedays.policy

import com.depromeet.threedays.navigator.PolicyNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PolicyModule {

    @Binds
    abstract fun bindPolicyNavigator(navi: PolicyNavigatorImpl): PolicyNavigator
}
