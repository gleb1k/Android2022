package com.example.android2022

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var musicAdapter: MusicAdapter? = null

    private var binder: MusicService.MusicBinder? = null

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

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        musicAdapter = MusicAdapter(
            MusicRepository.musicList,
            ::playMusic,
            ::showMusicInfo
        )

        if (arguments?.getString(MAIN_FRAGMENT_TAG) == "ShowInfoFragment")
        {
            showMusicInfo(MusicRepository.currentId)
        }

        binding?.run {
            rvMusic.adapter = musicAdapter
        }


    }


    private fun playMusic(position: Int) {
        //колхоз
        musicAdapter?.notifyDataSetChanged()
        binder?.startMusic(position)
    }

    private fun showMusicInfo(position: Int) {
        if (position != MusicRepository.currentId)
        binder?.startMusic(position)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
            )
            .replace(
                R.id.fragment_container,
                MusicInfoFragment.newInstance(position.toString()),
                MusicInfoFragment.MUSIC_INFO_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }
    companion object {

        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

        fun newInstance(data: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(MAIN_FRAGMENT_TAG, data)
                }
            }
    }

}
