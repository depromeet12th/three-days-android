package com.depromeet.threedays.firebase

import com.depromeet.threedays.data.datasource.notification.token.NotificationTokenLocalDataSource
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun bindsFirebaseMessagingProvide(): FirebaseMessaging = FirebaseMessaging.getInstance()

    @Singleton
    @Provides
    fun bindsFirebaseTokenDataSourceProvide(
        firebaseTokenDataSource: FirebaseTokenDataSource
    ): NotificationTokenLocalDataSource = firebaseTokenDataSource
}
