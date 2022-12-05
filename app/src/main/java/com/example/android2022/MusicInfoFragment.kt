package com.example.android2022

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentMusicInfoBinding
import java.lang.Exception

class MusicInfoFragment : Fragment(R.layout.fragment_music_info) {

    private var binding: FragmentMusicInfoBinding? = null

    private var binder: MusicService.MusicBinder? = null

    private var music: Music? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? MusicService.MusicBinder
            initialiseSeekBar()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bindService(
            Intent(activity, MusicService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
        val musicId = arguments?.getString(MUSIC_INFO_FRAGMENT_TAG)!!.toInt()
        MusicRepository.currentId = musicId
        music = MusicRepository.musicList[musicId]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicInfoBinding.bind(view)

        binding?.run {
            generateView()

            ivPlay.setOnClickListener {
                binder?.playPauseMusic()
                generatePlayPause()
            }
            ivSkipBack.setOnClickListener {
                binder?.prevMusic()
                music = MusicRepository.musicList[MusicRepository.currentId]
                generateView()
                initialiseSeekBar()
            }
            ivSkipNext.setOnClickListener {
                binder?.nextMusic()
                music = MusicRepository.musicList[MusicRepository.currentId]
                generateView()
                initialiseSeekBar()
            }

            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) binder?.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        }
    }

    //старое, но как сделать правильно не знаю
    private fun initialiseSeekBar() {
        if (binder != null) {
            binding?.run {
                seekbar.max = binder?.mediaPlayerDuration()!!

                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        try {
                            seekbar.progress = binder?.mediaPlayerCurrentPosition()!!
                            handler.postDelayed(this, 1000)
                        } catch (e: Exception) {
                            seekbar.progress = 0
                        }
                    }
                }, 0)
            }
        }

    }


    private fun generateView() {
        binding?.run {
            ivCover.setImageResource(music!!.cover)
            tvAuthor.text = music?.author
            tvName.text = music?.name
            tvGenre.text = music?.genre
        }
        generatePlayPause()
    }

    private fun generatePlayPause() {
        binding?.run {
            if (MusicRepository.isPlaying) {
                ivPlay.setImageResource(R.drawable.ic_round_pause_24)
            } else {
                ivPlay.setImageResource(R.drawable.ic_round_play_arrow_24)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val MUSIC_INFO_FRAGMENT_TAG = "MUSIC_INFO_FRAGMENT_TAG"

        fun newInstance(data: String) =
            MusicInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(MUSIC_INFO_FRAGMENT_TAG, data)
                }
            }
    }
}