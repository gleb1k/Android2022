package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android2022.databinding.FragmentMainBinding
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    private var adapter: CarAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        val glide = Glide.with(this@MainFragment)
        CarRepository.cars.forEach{
            glide.load(it.url).preload()
        }

        binding?.run{
            adapter = CarAdapter(
                CarRepository.cars,
                glide,
                onItemClick = :: onItemWasClicked
            )
            rvCars.adapter = adapter

            rvCars.adapter = ScaleInAnimationAdapter(adapter!!).apply {
                // Change the durations.
                setDuration(500)
                // Disable the first scroll mode.
                setFirstOnly(false)
            }

        }

    }

    private fun onItemWasClicked (position : Int) {

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

        fun getInstance(message: String)
            = MainFragment().apply {
                arguments = Bundle().apply {
                    putString(MAIN_FRAGMENT_TAG,message)

            }
        }
    }
}