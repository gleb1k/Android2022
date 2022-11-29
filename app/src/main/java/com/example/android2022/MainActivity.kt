package com.example.android2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var adapter: MusicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding?.run {
            adapter = MusicAdapter(
                MusicRepository.musicList,
                :: playMusic,
                :: showMusicInfo
                )


        }

    }

    private fun playMusic(music: Music) {

    }

    private fun showMusicInfo(music: Music) {

    }

}