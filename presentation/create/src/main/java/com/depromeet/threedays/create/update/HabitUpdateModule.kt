package com.depromeet.threedays.create.update

import com.depromeet.threedays.navigator.HabitUpdateNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HabitUpdateModule {

    @Binds
    abstract fun bindHabitUpdateNavigator(navi: HabitUpdateNavigatorImpl): HabitUpdateNavigator
}
