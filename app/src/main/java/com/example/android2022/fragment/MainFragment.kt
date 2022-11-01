package com.example.android2022.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android2022.R
import com.example.android2022.SwipeGesture
import com.example.android2022.adapter.CarListAdapter
import com.example.android2022.databinding.FragmentMainBinding
import com.example.android2022.model.MainItem
import com.example.android2022.model.Repository
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    private var adapter: CarListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        Repository.createList()

        val glide = Glide.with(this@MainFragment)

        binding?.run {

            adapter = CarListAdapter(
                glide,
                onItemClick = ::onItemWasClicked,
                actionDelete = ::onDeleteWasClicked,
            )
            rvCars.adapter = adapter

            val swipeGesture = SwipeGesture(adapter!!)
            swipeGesture.attachToRecyclerView(rvCars)

            rvCars.adapter = ScaleInAnimationAdapter(adapter!!).apply {
                // Change the durations.
                setDuration(500)
                // Disable the first scroll mode.
                setFirstOnly(false)
            }

            btnFloatingAdd.setOnClickListener {
                AddDialogFragment(actionAdd = ::addItem).show(
                    childFragmentManager, AddDialogFragment.ADD_DIALOG_TAG
                )
            }

            adapter?.submitList(Repository.dataList)

        }

    }

    private fun addItem(position: Int, car: MainItem.Car) {
        Repository.addItem(position, car)

        adapter?.submitList(Repository.dataList)
    }

    //Удаление элемента
    private fun onDeleteWasClicked(car: MainItem.Car) {

        Repository.deleteItem(car)

        adapter?.submitList(Repository.dataList)
    }

    private fun onItemWasClicked(position: Int) {

        adapter?.notifyItemChanged(position)

//        parentFragmentManager.beginTransaction()
//            .setCustomAnimations(
//                androidx.appcompat.R.anim.abc_fade_in,
//                androidx.appcompat.R.anim.abc_fade_out,
//                androidx.appcompat.R.anim.abc_fade_in,
//                androidx.appcompat.R.anim.abc_fade_out,
//            )
//            .replace(
//                R.id.fragment_container,
//                CarInfoFragment.getInstance(position.toString()),
//                CarInfoFragment.CAR_INFO_FRAGMENT_TAG
//            )
//            .addToBackStack("arg")
//            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

        fun getInstance(message: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(MAIN_FRAGMENT_TAG, message)

            }
        }
    }
}