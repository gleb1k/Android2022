package com.example.android2022

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(
    private var list : List<Music>,
    private var actionPlay : (Music) -> Unit,
    private var actionShowInfo : (Music) -> Unit
) : RecyclerView.Adapter<MusicHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder.create(parent,actionShowInfo,actionPlay)
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}