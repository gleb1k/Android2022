package com.example.android2022.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2022.data.Note
import com.example.android2022.databinding.ItemNoteGridBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteHolder(
    private val binding: ItemNoteGridBinding,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(note : Note) {
        binding.run {
            tvTitle.text = note.title
            tvDescription.text = note.description
//            var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
//            val newDate: Date = spf.parse(note.date.toString())
//            spf = SimpleDateFormat("dd MMM yyyy")
//            val dateRes = spf.format(newDate)
            tvDate.text = note.date.toString()
            //ПОЧЕМУ НЕ РАБОТАЕТ ROOT КАК КЛИК ЛИСТЕНЕР???
            //Я хотел чтоб вся панелька была кликабельна(
            //а в итоге получился мусор
            root.setOnClickListener{
                onItemClick(note.id)
            }
            tvTitle.setOnClickListener{
                onItemClick(note.id)
            }
            tvDate.setOnClickListener{
                onItemClick(note.id)
            }
            tvDescription.setOnClickListener{
                onItemClick(note.id)
            }
            ivDelete.setOnClickListener {
                onDeleteItem(note.id)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (Int) -> Unit,
            onDeleteItem: (Int) -> Unit
        ) = NoteHolder(
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