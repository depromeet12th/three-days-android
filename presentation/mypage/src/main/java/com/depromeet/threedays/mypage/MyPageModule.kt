package com.depromeet.threedays.mypage

import com.depromeet.threedays.mypage.archived_habit.ArchivedHabitNavigatorImpl
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MyPageModule {
    @Binds
    abstract fun bindArchivedHabitNavigator(navigator: ArchivedHabitNavigatorImpl): ArchivedHabitNavigator
}
