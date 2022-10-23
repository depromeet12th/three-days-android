package com.depromeet.threedays.register.add

import com.depromeet.threedays.navigator.GoalAddNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GoalAddModule {

    @Binds
    abstract fun bindGoalAddNavigator(navi: GoalAddNavigatorImpl): GoalAddNavigator
}
