package com.depromeet.threedays.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.depromeet.threedays.home.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG, "onMessageReceived. notification: ${message.notification}, data: ${message.data}")

        val title = message.notification?.title.orEmpty()
        val body = message.notification?.body.orEmpty()

        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "ChannelID"
        val mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "ChannelName"
            val channelDescription = "ChannelDescription"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 100, 200)
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }
            mManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(CoreDesignSystemResources.drawable.ic_logo_app)
            setAutoCancel(true)
            setDefaults(Notification.DEFAULT_ALL)
            setWhen(System.currentTimeMillis())
            setContentTitle(title)
            setContentText(body)
            setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setContentTitle(title)
            builder.setVibrate(longArrayOf(500, 500))
        }
        mManager.notify(0, builder.build())
    }

    companion object {
        const val TAG = "FCMService"
    }

}
