package com.depromeet.threedays.firebase

import com.depromeet.threedays.data.datasource.notification.token.NotificationTokenLocalDataSource
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebaseTokenDataSource @Inject constructor() : NotificationTokenLocalDataSource {
    override suspend fun getToken(): String? {
        val registrationToken = runBlocking {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Timber.e("Failed to get registration token")
                    }
                }.await()
        }
        return registrationToken
    }
}
