package com.example.android2022

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.databinding.ItemCarBinding

class CarItem(
    private val binding: ItemCarBinding,
    private val glide: RequestManager,
    private val onItemClick : (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {



    fun onBind(car: Car) {
        with(binding) {
            tvTitle.text = car.name
            tvBrand.text = car.brand

            glide
                .load(car.url)
                .into(ivCover)

            root.setOnClickListener{
                onItemClick(car.id)
            }
        }
    }
}