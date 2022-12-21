package com.depromeet.threedays.create.create

import com.depromeet.threedays.navigator.HabitCreateNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HabitCreateModule {

    @Binds
    abstract fun bindHabitCreateNavigator(navi: HabitCreateNavigatorImpl): HabitCreateNavigator
}
