package com.example.android2022.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.databinding.ItemAdvertisementBinding
import com.example.android2022.databinding.ItemCarBinding

class AdvertisementItem(
    val binding: ItemAdvertisementBinding,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(ad: MainItem.Advertisement) {
        with(binding)
        {
            tvTittle.text = ad.title

            glide.load(ad.url)
                .into(ivCover)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager
        ) = AdvertisementItem(
            binding = ItemAdvertisementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide
        )
    }
}