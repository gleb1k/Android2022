package com.example.android2022

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var musicNotification: MusicNotification

    override fun onCreate() {
        super.onCreate()
        musicNotification = MusicNotification(context = this)
    }

    inner class MusicBinder : Binder() {

        fun startMusic(musicId: Int) {
            set(musicId)
            playPause()
        }

        fun playPauseMusic() {
            if (mediaPlayer == null)
                startMusic(MusicRepository.currentId)
            playPause()
        }

        //не юзается
        fun stopMusic() {
            stop()
        }

        fun nextMusic() {
            next()
        }

        fun prevMusic() {
            prev()
        }

        fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        fun mediaPlayerDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        fun mediaPlayerCurrentPosition(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }
    }


    override fun onBind(intent: Intent?): IBinder = MusicBinder()

    fun prev() {
        mediaPlayer?.stop()
        MusicRepository.newPrev()
        musicNotification.updateNotification()
        set(MusicRepository.currentId)
        playPause()
    }

    fun next() {
        mediaPlayer?.stop()
        MusicRepository.newNext()
        musicNotification.updateNotification()
        set(MusicRepository.currentId)
        playPause()
    }

    fun stop() {
        mediaPlayer?.stop()
        musicNotification.createNotification()
    }

    fun set(musicId: Int): Boolean {
        MusicRepository.currentId
        mediaPlayer?.stop()
        mediaPlayer =
            MediaPlayer.create(applicationContext, MusicRepository.musicList[musicId].audio)
        MusicRepository.currentId = musicId
        musicNotification.createNotification()
        return true
    }

    private fun playPause() {

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            MusicRepository.isPlaying = false

        } else {
            mediaPlayer?.run {
                start()
                MusicRepository.isPlaying = true
                setOnCompletionListener {
                    stop()
                }
            }
        }
        musicNotification.createNotification() //колхоз

    }

    //перехватываю кнопочки уведомляшки
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_NEXT -> next()
            ACTION_PREV -> prev()
            ACTION_PLAY -> playPause()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    companion object {
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREV = "ACTION_PREV"
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_STOP = "ACTION_STOP"
    }

}