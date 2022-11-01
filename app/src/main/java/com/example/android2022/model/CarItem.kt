package com.example.android2022.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.R
import com.example.android2022.databinding.ItemCarBinding

class CarItem(
    private val binding: ItemCarBinding,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
    private val actionDelete: (MainItem.Car) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(car: MainItem.Car) {
        with(binding) {
            tvTitle.text = car.name
            tvBrand.text = car.brand

            glide
                .load(car.url)
                .into(ivCover)

            ivDelete.setOnClickListener {
                actionDelete(car)
            }
        }
    }
//    fun onBind(
//        book: MainItem.Car,
//        payloads: MutableList<Any>
//    ) {
//        (payloads.last() as? Bundle)?.also { bundle ->
//            updateFromBundle(bundle)
//        }
//    }
//
//    fun onBind(
//        book: MainItem.Car,
//        bundle: Bundle
//    ) {
//        updateFromBundle(bundle)
//    }

//    fun updateFromBundle(bundle: Bundle?) {
//        if (bundle?.containsKey(ARG_BRAND) == true) {
//            bundle.getString(ARG_BRAND).also {
//                binding.tvBrand.text = it
//            }
//        }
//        if (bundle?.containsKey(ARG_NAME) == true) {
//            bundle.getString(ARG_NAME).also {
//                binding.tvTitle.text = it
//            }
//        }
//        binding.ivCover.setImageResource(R.drawable.cote)
//    }

    companion object {

        const val ARG_NAME = "ARG_NAME"
        const val ARG_BRAND = "ARG_BRAND"

        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            onItemClick: (Int) -> Unit,
            actionDelete: (MainItem.Car) -> Unit,
        ) = CarItem(
            binding = ItemCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = onItemClick,
            actionDelete = actionDelete
        )
    }
}