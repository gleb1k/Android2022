package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null

    private var counter :  Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        //почему не возвращается значение?
        if (savedInstanceState != null)
            counter = savedInstanceState.getInt(ARG_COUNTER)

        parentFragmentManager.popBackStack(SecondFragment.SECOND_FRAGMENT_TAG, 1)

        binding?.run {

                tvCounter.text = showCounter(counter)

            btn1.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                    )
                    .replace(
                        R.id.fragment_container,
                        SecondFragment.newInstance(counter.toString()),
                        SecondFragment.SECOND_FRAGMENT_TAG
                    )
                    .addToBackStack(SecondFragment.SECOND_FRAGMENT_TAG)
                    .commit()
            }

            btn2.setOnClickListener {
                counter++
                tvCounter.text = showCounter(counter)
            }

            btn3.setOnClickListener {
                val dialog = CustomDialogFragment(counter) {
                    counter = it
                    tvCounter.text = showCounter(counter)
                }
                dialog.show(parentFragmentManager,"custom dialog")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_COUNTER,counter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    //юселес
    companion object {
        private const val ARG_COUNTER = "COUNTER"

        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

        fun newInstance(name : String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_COUNTER, name)
            }
        }
        //

        fun showCounter(counter: Int): String {
            return if (counter < 0 ) {
                "invalid values"
            } else
                "Counter value: $counter"

        }
    }
}


