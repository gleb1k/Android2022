package com.example.android2022

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer = MediaPlayer()

    inner class MusicBinder : Binder() {

        fun playPauseMusic(musicId: Int) {
            if (MusicRepository.currentId != musicId) {
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(applicationContext, MusicRepository.musicList[musicId].audio)
                MusicRepository.currentId = musicId
            }
            playPause()
        }

        fun nextMusic() {
            next()
        }

        fun prevMusic() {
            prev()
        }

        fun seekTo(progress: Int){
            mediaPlayer.seekTo(progress)
        }
        fun mediaPlayerDuration() : Int{
            return mediaPlayer.duration
        }
        fun mediaPlayerCurrentPosition() : Int{
            return mediaPlayer.currentPosition
        }
    }


    override fun onBind(intent: Intent?): IBinder = MusicBinder()

    fun prev(){
        mediaPlayer.stop()
        mediaPlayer =
            MediaPlayer.create(applicationContext, MusicRepository.prev(MusicRepository.currentId).audio)
        playPause()
    }

    fun next()
    {
        mediaPlayer.stop()
        mediaPlayer =
            MediaPlayer.create(applicationContext, MusicRepository.next(MusicRepository.currentId).audio)
        playPause()
    }

    private fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            MusicRepository.isPlaying = false
            return
        }
        mediaPlayer.run {
            start()
            MusicRepository.isPlaying = true
            setOnCompletionListener {
                next()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}