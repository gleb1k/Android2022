package com.example.android2022.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.android2022.model.CarItem
import com.example.android2022.model.MainItem
import com.example.android2022.model.Repository

class CarListAdapter(
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : ListAdapter<MainItem.Car, CarItem>(
    object : DiffUtil.ItemCallback<MainItem.Car>() {
        override fun areItemsTheSame(
            oldItem: MainItem.Car,
            newItem: MainItem.Car
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MainItem.Car,
            newItem: MainItem.Car
        ): Boolean = oldItem == newItem

        override fun getChangePayload(
            oldItem: MainItem.Car,
            newItem: MainItem.Car
        ): Any? {
            val bundle = Bundle()
            if (oldItem.name != newItem.name) {
                bundle.putString(CarItem.ARG_NAME, newItem.name)
            }
            if (oldItem.brand != newItem.brand) {
                bundle.putString(CarItem.ARG_BRAND, newItem.brand)
            }
            return if (bundle.isEmpty) super.getChangePayload(oldItem, newItem) else bundle
        }
    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarItem = CarItem.create(parent, glide, onItemClick)

    override fun onBindViewHolder(
        holder: CarItem,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CarItem,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.updateFromBundle(payloads.last() as? Bundle)
        }
    }

}