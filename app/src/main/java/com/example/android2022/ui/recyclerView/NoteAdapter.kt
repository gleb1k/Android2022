package com.example.android2022.ui.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android2022.data.Note

class NoteAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onDeleteItem: (Int) -> Unit
) :
    ListAdapter<Note, NoteHolder>(object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder =
        NoteHolder.create(parent, onItemClick, onDeleteItem)

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.onBind(currentList[position])
    }

}