package com.example.android2022

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var binding: FragmentSecondBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        val counter = arguments?.getString(ARG_COUNTER)

        binding?.run {
            tv.text = MainFragment.showCounter(counter!!.toInt())
            when (counter!!.toInt()) {
                in 0 .. 50 -> secondScreen.setBackgroundColor(Color.parseColor("#BC12C3"));
                in 51 .. 100 -> secondScreen.setBackgroundColor(Color.parseColor("#CC5302"));
                in 101 .. Int.MAX_VALUE -> secondScreen.setBackgroundColor(Color.parseColor("#1DF004"));
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



    companion object {
        const val ARG_COUNTER = "ARG_COUNTER"

        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"

        fun newInstance(name: String) = SecondFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_COUNTER, name)
            }
        }
    }
}


