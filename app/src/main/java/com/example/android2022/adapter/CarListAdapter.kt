package com.example.android2022.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.R
import com.example.android2022.model.AdvertisementItem
import com.example.android2022.model.CarItem
import com.example.android2022.model.MainItem
import com.example.android2022.model.Repository

class CarListAdapter(
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
    private val actionDelete: (MainItem.Car) -> Unit,

    ) : ListAdapter<MainItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<MainItem>() {

        override fun areItemsTheSame(
            oldItem: MainItem,
            newItem: MainItem
        ): Boolean {
            if (oldItem is MainItem.Car && newItem is MainItem.Car)
                return (oldItem.name == newItem.name && oldItem.info == newItem.info)
            if (oldItem is MainItem.Advertisement && newItem is MainItem.Advertisement)
                return oldItem.title == newItem.title
            return false
        }

        override fun areContentsTheSame(
            oldItem: MainItem,
            newItem: MainItem
        ): Boolean = oldItem == newItem

//        override fun getChangePayload(
//            oldItem: MainItem.Car,
//            newItem: MainItem.Car
//        ): Any? {
//            val bundle = Bundle()
//            if (oldItem.name != newItem.name) {
//                bundle.putString(CarItem.ARG_NAME, newItem.name)
//            }
//            if (oldItem.brand != newItem.brand) {
//                bundle.putString(CarItem.ARG_BRAND, newItem.brand)
//            }
//            return if (bundle.isEmpty) super.getChangePayload(oldItem, newItem) else bundle
//        }
    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): //CarItem = CarItem.create(parent, glide, onItemClick, actionDelete)
            RecyclerView.ViewHolder = when (viewType) {
        R.layout.item_car -> CarItem.create(parent, glide, onItemClick, actionDelete)
        R.layout.item_advertisement -> AdvertisementItem.create(parent, glide)
        else -> throw IllegalStateException("Don't implement view type")
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = currentList[position]) {
            is MainItem.Car -> (holder as CarItem).onBind(item)
            is MainItem.Advertisement -> (holder as AdvertisementItem).onBind(item)
        }
    }

    override fun getItemViewType(position: Int)
            : Int {
        return when (currentList[position]) {
            is MainItem.Car -> R.layout.item_car
            is MainItem.Advertisement -> R.layout.item_advertisement
        }
    }
}

//    override fun onBindViewHolder(
//        holder: CarItem,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
//        } else {
//            holder.updateFromBundle(payloads.last() as? Bundle)
//        }
//    }

