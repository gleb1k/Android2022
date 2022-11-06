package com.example.android2022

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmapOrNull

class NotificationProvider(private val context: Context, private val awakeActivity: AwakeActivity) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showNotification(title: String, text: String) {

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val music: Uri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.feel_good_gachi
        )
        val vibration = arrayOf(300L, 100L).toLongArray()

        val intent = Intent(context, AwakeActivity::class.java).also{
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pending = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create an Intent for the activity you want to start
//        val resultIntent = Intent(awakeActivity, AwakeActivity::class.java)
//        // Create the TaskStackBuilder
//        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(awakeActivity).run {
//            // Add the intent, which inflates the back stack
//            addNextIntentWithParentStack(resultIntent)
//            // Get the PendingIntent containing the entire back stack
//            getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.default_notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(false)
                .setContentIntent(pending)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("♂Do you like my two fat cocks♂\n" +
                            "\t♂Fisting is 300 bucks♂\n" +
                            "\t♂Do you like amazing sex?♂\n" +
                            "\t♂Stick your finger in my ass♂\n" +
                            "\t♂It so fuckin deep , my master♂\n" +
                            "\t♂That's amazing!♂ ♂Thanks your , sir!♂\n" +
                            "\t♂Do you like 300 anal?♂\n" +
                            "\t♂Master...♂\n" +
                            "\t♂ASS WE CAN!!!♂"))


        context.getDrawable(R.drawable.gachi)?.toBitmapOrNull()?.also {
            builder.setLargeIcon(it)
        }

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
                notificationManager.createNotificationChannel(it)
            }

        } else {

            builder
                .setVibrate(vibration)
                .setSound(music)
                .setLights(Color.BLUE, 100, 500)
        }

        notificationManager.notify(2132, builder.build())

    }
}