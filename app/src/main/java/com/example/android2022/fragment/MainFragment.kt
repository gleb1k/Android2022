package com.example.android2022.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android2022.R
import com.example.android2022.adapter.CarListAdapter
import com.example.android2022.databinding.FragmentMainBinding
import com.example.android2022.model.Repository
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    private var adapter: CarListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        val glide = Glide.with(this@MainFragment)

        binding?.run {
            adapter = CarListAdapter(
                glide,
                onItemClick = ::onItemWasClicked
            )
            rvCars.adapter = adapter

            rvCars.adapter = ScaleInAnimationAdapter(adapter!!).apply {
                // Change the durations.
                setDuration(500)
                // Disable the first scroll mode.
                setFirstOnly(false)
            }

            fab.setOnClickListener {
                AddDialogFragment().show(
                    childFragmentManager, AddDialogFragment.ADD_DIALOG_TAG
                )
            }
            adapter?.submitList(Repository.carsMutable) {
//                rvBook.scrollToPosition(0)
            }

        }

    }

//    private fun onDeleteClicked(position: Int) {
//        CarRepository.deleteItem(position)
//        adapter?.submitList(Repository.dataList)
//    }
//
//    private fun addCustomItem(position: Int, item: MyModel.Item) {
//        Repository.addItem(position, item)
//        adapter?.submitList(Repository.dataList)
//    }

    private fun onItemWasClicked(position: Int) {

        adapter?.notifyItemChanged(position)

        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
            )
            .replace(
                R.id.fragment_container,
                CarInfoFragment.getInstance(position.toString()),
                CarInfoFragment.CAR_INFO_FRAGMENT_TAG
            )
            .addToBackStack("arg")
            .commit()
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