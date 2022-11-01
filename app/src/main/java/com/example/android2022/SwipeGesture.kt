package com.example.android2022

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android2022.adapter.CarListAdapter
import com.example.android2022.model.MainItem
import com.example.android2022.model.Repository


class SwipeGesture(adapter: CarListAdapter?) :
    ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val list = Repository.dataList
            val item = list[viewHolder.adapterPosition]
            Repository.deleteItem(item)
            adapter?.submitList(Repository.dataList)

        }
    }
    )