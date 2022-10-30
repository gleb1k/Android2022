package com.example.android2022.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android2022.R
import com.example.android2022.databinding.ItemCarBinding
import com.example.android2022.model.AdvertisementItem
import com.example.android2022.model.CarItem
import com.example.android2022.model.MainItem
import java.lang.IllegalStateException

class CarAdapter(
    private val list: List<MainItem.Car>,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType){
        R.layout.item_car -> CarItem.create(parent,glide,onItemClick)
        R.layout.item_advertisement -> AdvertisementItem.create(parent, glide)
        else -> throw  IllegalStateException("ошибка")

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when {
            holder is CarItem -> holder.onBind(list[position])
//            holder is AdvertisementItem -> holder.onBind(list[position])

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
//            holder.onBind(list[position], payloads)
//        }
//    }

    override fun getItemCount(): Int = list.size
}