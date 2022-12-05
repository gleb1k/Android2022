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
        music = MusicRepository.musicList[musicId]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicInfoBinding.bind(view)
        val musicId = MusicRepository.musicList.indexOfFirst { music == it }

        binding?.run {
            generateView()

            ivPlay.setOnClickListener {
                binder?.playPauseMusic(musicId)
            }
            ivSkipBack.setOnClickListener {
                binder?.prevMusic()
                music = MusicRepository.prev(musicId)
                generateView()
            }
            ivSkipNext.setOnClickListener {
                binder?.nextMusic()
                music = MusicRepository.next(musicId)
                generateView()
            }
//            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(
//                    seekBar: SeekBar?,
//                    progress: Int,
//                    fromUser: Boolean
//                ) {
//                    if (fromUser) binder?.seekTo(progress)
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                }
//
//                override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                }
//
//            })
        }
    }
//    private fun initialiseSeekBar(){
//        binding?.run{
//            seekbar.max = binder?.mediaPlayerDuration()!!
//
//            val handler = Handler()
//            handler.postDelayed(object: Runnable {
//                override fun run() {
//                    try {
//                        seekbar.progress = binder?.mediaPlayerCurrentPosition()!!
//                        handler.postDelayed(this,1000)
//                    } catch (e: Exception)
//                    {
//                        seekbar.progress = 0
//                    }
//                }
//            },0)
//        }
//
//    }


    private fun generateView() {
        binding?.run {
            ivCover.setImageResource(music!!.cover)
            tvAuthor.text = music?.author
            tvName.text = music?.name
            tvGenre.text = music?.genre

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