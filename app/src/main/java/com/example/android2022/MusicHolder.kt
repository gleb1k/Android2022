package com.example.android2022

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2022.databinding.ItemMusicBinding

class MusicHolder(
    private val binding: ItemMusicBinding,
    private val actionShowInfo: (Int) -> Unit,
    private val actionPlay: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(music: Music) {
        with(binding) {
            tvAuthor.text = music.author
            tvName.text = music.name
            ivCover.setImageResource(music.cover)


            if (MusicRepository.musicList.indexOfFirst { music == it } == MusicRepository.currentId)
            {
                root.setBackgroundColor(Color.parseColor("#86F0F0"))
            }
            else
            {
                root.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            ivCover.setOnClickListener {
               actionShowInfo(MusicRepository.musicList.indexOfFirst { music == it })
            }
            tvName.setOnClickListener {
                actionShowInfo(MusicRepository.musicList.indexOfFirst { music == it })
            }
            tvAuthor.setOnClickListener {
                actionShowInfo(MusicRepository.musicList.indexOfFirst { music == it })
            }

            root.setOnClickListener {
                actionPlay(MusicRepository.musicList.indexOfFirst { music == it })
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            actionShowInfo: (Int) -> Unit,
            actionPlay: (Int) -> Unit
        ) = MusicHolder(
            binding = ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionShowInfo,
            actionPlay
        )
    }

}