package com.depromeet.threedays.mate.create.step1

import com.depromeet.threedays.navigator.ConnectHabitNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConnectHabitModule {

    @Binds
    abstract fun bindConnectHabitNavigator(navi: ConnectHabitNavigatorImpl): ConnectHabitNavigator
}
