package com.example.android2022.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.android2022.data.Note
import com.example.android2022.databinding.ItemNoteGridBinding
import kotlinx.coroutines.launch

class NoteHolder(
    private val binding: ItemNoteGridBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(note : Note) {
        binding.run {
            tvTitle.text = note.title
            tvDescription.text = note.description
            root.setOnClickListener{
                onItemClick(note.id)
            }
            tvDescription.setOnClickListener{
                onItemClick(note.id)
            }
            ivDelete.setOnClickListener {
                //todo
                lifecycleScope.launch {
                }
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (Int) -> Unit
        ) = NoteHolder(
            binding = ItemNoteGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )

    }

}