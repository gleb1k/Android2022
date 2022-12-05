package com.example.android2022

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat


class MusicNotification(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val nextIntent = Intent(context, MusicService::class.java).apply {
        action = MusicService.ACTION_NEXT
    }

    private val nextPendingIntent = PendingIntent.getService(
        context,
        0,
        nextIntent,
        PendingIntent.FLAG_MUTABLE
    )

    private val playIntent = Intent(context, MusicService::class.java).apply {
        action = MusicService.ACTION_PLAY
    }
    private val playPendingIntent = PendingIntent.getService(
        context,
        0,
        playIntent,
        PendingIntent.FLAG_MUTABLE
    )

    private val prevIntent = Intent(context, MusicService::class.java).apply {
        action = MusicService.ACTION_PREV
    }

    private val prevPendingIntent = PendingIntent.getService(
        context,
        0,
        prevIntent,
        PendingIntent.FLAG_MUTABLE
    )

    fun createNotification() {
        val currentMusic = MusicRepository.musicList[MusicRepository.currentId]
        val cover = BitmapFactory.decodeResource(context.resources, currentMusic.cover)

        //логика по отображению конкретной песни будет потом, возьму id из репозитория
        val infoIntent = Intent(context, MainActivity::class.java).also {
//            it.putExtra(SHOW_INFO_FRAGMENT,"MusicInfoFragment") //?
            it.action = SHOW_INFO_FRAGMENT
        }
        val infoPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, infoIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, infoIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(currentMusic.name)
            .setContentText(currentMusic.author)
            .setAutoCancel(false)
            .setSilent(true)
            .setLargeIcon(cover)
            .setSmallIcon(R.drawable.ic_round_play_arrow_24)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(infoPendingIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .addAction(R.drawable.ic_round_skip_prev_24, "Previous", prevPendingIntent)

        //в идеале надо обновлять существующую уведомляшку, наверное
        if (MusicRepository.isPlaying) {
            builder
                .addAction(R.drawable.ic_round_pause_24, "Pause", playPendingIntent)
        }
        else
        {
            builder
                .addAction(R.drawable.ic_round_play_arrow_24, "Play", playPendingIntent)
        }
        builder.addAction(R.drawable.ic_round_skip_next_24, "Next", nextPendingIntent)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    //когда обновляется уведомление, странно все прыгает
    fun updateNotification() {
        val currentMusic = MusicRepository.musicList[MusicRepository.currentId]
        val cover = BitmapFactory.decodeResource(context.resources, currentMusic.cover)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(currentMusic.name)
            .setContentText(currentMusic.author)
            .setLargeIcon(cover)
            .setSmallIcon(R.drawable.ic_round_play_arrow_24)
//            .mActions.clear()  попытался сделать изменение иконочки play pause, но не получилось

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun closeNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                "Music",
                NotificationManager.IMPORTANCE_LOW
            ).also {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    companion object {
        const val SHOW_INFO_FRAGMENT = "SHOW_INFO_FRAGMENT"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "MUSIC_CHANNEL"
    }
}