package com.example.android2022

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat

class MyNotificationChannel(
    private val context: Context,
//    private val builder: NotificationCompat.Builder
) {
//    var notificationManager : NotificationManager? = null

    fun createNotificationChannel(){

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val music: Uri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.feel_good_gachi
        )
        val vibration = arrayOf(300L, 100L).toLongArray()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.default_notification_channel_id),
                context.getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                vibrationPattern = vibration
                setSound(music, audioAttributes)
                setShowBadge(true)
            }.also {
                notificationManager?.createNotificationChannel(it)
            }
        } else {
//            builder
//                .setVibrate(vibration)
//                .setSound(music)
//                .setLights(Color.BLUE, 100, 500)
        }
    }
}