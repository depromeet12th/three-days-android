package com.depromeet.threedays.register.update

import com.depromeet.threedays.navigator.GoalUpdateNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GoalUpdateModule {

    @Binds
    abstract fun bindGoalUpdateNavigator(navi: GoalUpdateNavigatorImpl): GoalUpdateNavigator
}
