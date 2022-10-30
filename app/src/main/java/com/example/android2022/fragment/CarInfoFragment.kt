package com.example.android2022.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android2022.R
import com.example.android2022.databinding.FragmentCarInfoBinding
import com.example.android2022.model.Repository

class CarInfoFragment : Fragment(R.layout.fragment_car_info) {

    private var binding: FragmentCarInfoBinding? = null

    private var carID: Int = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCarInfoBinding.bind(view)

        carID = arguments?.getString(CAR_INFO_FRAGMENT_TAG)!!.toInt()
        val car = Repository.cars[carID]

        binding?.run {
            collapsingToolbar.title = "Brand: ${car.brand}, Name: ${car.name} "
            tvInfo.text = car.info
            Glide.with(root)
                .load(car.url)
                .error(R.drawable.ic_sharp_directions_car_24)
                .into(ivCar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val CAR_INFO_FRAGMENT_TAG = "CAR_INFO_FRAGMENT_TAG"

        fun getInstance(message: String) = CarInfoFragment().apply {
            arguments = Bundle().apply {
                putString(CAR_INFO_FRAGMENT_TAG, message)

            }
        }
    }

}