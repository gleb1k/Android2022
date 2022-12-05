package com.example.android2022

import MusicPackage.Music
import MusicPackage.MusicAdapter
import MusicPackage.MusicInfoFragment
import MusicPackage.MusicRepository
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.widget.Toast
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
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

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this@MainActivity, MusicService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        musicAdapter = MusicAdapter(
            MusicRepository.musicList,
            ::playMusic,
            ::showMusicInfo
        )

        binding?.run {
            rvMusic.adapter = musicAdapter;
        }

    }

    private fun playMusic(music: Music) {
        binder?.playMusic(music)
    }

    private fun showMusicInfo(position: Int) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
            )
            .add(
                R.id.fragment_container,
                MusicInfoFragment.newInstance(position.toString()),
                MusicInfoFragment.MUSIC_INFO_FRAGMENT_TAG
            )
            .addToBackStack(MusicInfoFragment.MUSIC_INFO_FRAGMENT_TAG)
            .commit()
    }

}