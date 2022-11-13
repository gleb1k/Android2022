package com.example.android2022

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmapOrNull

class AlarmReceiver : BroadcastReceiver() {



    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onReceive(context: Context, intent: Intent?) {
        intent?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val i = Intent(context, AwakeActivity::class.java).also{
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pending = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT)
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.default_notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentTitle("AAAhlarm")
                .setContentText("Dungeon Master can't wait!!")
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

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        notificationManager.notify(1337, builder.build())
    }
}