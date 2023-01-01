package com.depromeet.threedays.notification

import com.depromeet.threedays.navigator.NotificationNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationHistoryModule {

    @Binds
    abstract fun bindNotificationNavigator(navi: NotificationHistoryNavigatorImpl): NotificationNavigator
}
