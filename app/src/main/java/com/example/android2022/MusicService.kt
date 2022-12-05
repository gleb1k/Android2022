package com.example.android2022

import MusicPackage.Music
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    private var mediaPlayer = MediaPlayer()

    inner class MusicBinder : Binder() {

        fun playMusic(music: Music) {
            play(music)
        }

        fun pauseMusic() {
            pause()
        }
    }


    override fun onBind(intent: Intent?): IBinder? = MusicBinder()

    private fun play(music: Music) {
        playLocaleMusic(music)
    }

    private fun pause() {
        mediaPlayer.pause()
    }

    private fun stop() {
        mediaPlayer.stop()
    }

    private fun playLocaleMusic(music: Music) {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer = MediaPlayer.create(applicationContext, music.audio)
        mediaPlayer.run {
            start()
            setOnCompletionListener {
                stop() // or call next() for change track
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}