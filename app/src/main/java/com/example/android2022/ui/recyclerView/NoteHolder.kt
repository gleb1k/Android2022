package com.example.android2022.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.android2022.data.Note
import com.example.android2022.databinding.ItemNoteGridBinding
import com.example.android2022.databinding.ItemNoteLinearBinding

class NoteHolder(
    private val binding: ViewBinding,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(note: Note) {
        binding.apply {
            if (this is ItemNoteGridBinding) {
                tvTitle.text = note.title
                tvDescription.text = note.description

                tvDate.text = note.date.toString()

                //ПОЧЕМУ НЕ РАБОТАЕТ ROOT КАК КЛИК ЛИСТЕНЕР???
                //Я хотел чтоб вся панелька была кликабельна(
                //а в итоге получился мусор
                root.setOnClickListener {
                    onItemClick(note.id)
                }
                tvTitle.setOnClickListener {
                    onItemClick(note.id)
                }
                tvDate.setOnClickListener {
                    onItemClick(note.id)
                }
                tvDescription.setOnClickListener {
                    onItemClick(note.id)
                }
                ivDelete.setOnClickListener {
                    onDeleteItem(note.id)
                }
            }
            if (this is ItemNoteLinearBinding) {
                tvTitle.text = note.title

                tvDate.text = note.date.toString()

                root.setOnClickListener {
                    onItemClick(note.id)
                }
                tvTitle.setOnClickListener {
                    onItemClick(note.id)
                }
                tvDate.setOnClickListener {
                    onItemClick(note.id)
                }
                ivDelete.setOnClickListener {
                    onDeleteItem(note.id)
                }
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            layoutManager: RecyclerView.LayoutManager,
            onItemClick: (Int) -> Unit,
            onDeleteItem: (Int) -> Unit
        ): NoteHolder {
            when (layoutManager) {
                is GridLayoutManager -> {
                    return NoteHolder(
                        binding = ItemNoteGridBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        onItemClick = onItemClick,
                        onDeleteItem = onDeleteItem
                    )
                }
                is LinearLayoutManager -> {
                    return NoteHolder(
                        binding = ItemNoteLinearBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        onItemClick = onItemClick,
                        onDeleteItem = onDeleteItem
                    )
                }
            }
            //какое-то базовое значение (костыль)
            return NoteHolder(
                binding = ItemNoteGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClick = onItemClick,
                onDeleteItem = onDeleteItem
            )
        }

    }

}