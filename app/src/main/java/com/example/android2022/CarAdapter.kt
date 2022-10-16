package com.example.android2022

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.databinding.ItemCarBinding

class CarAdapter(
    private val list: List<Car>,
    private val glide : RequestManager,
    private val onItemClick : (Int) -> Unit,
) : RecyclerView.Adapter<CarItem>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarItem {
        val carItem = CarItem(
            binding = ItemCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = onItemClick
        )
        return carItem
    }

    override fun onBindViewHolder(
        holder: CarItem,
        position: Int
    ) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}