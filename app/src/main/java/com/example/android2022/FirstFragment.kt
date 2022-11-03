package com.example.android2022

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import com.example.android2022.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFirstBinding.bind(view)


        val controller = findNavController()

        val options = navOptions {
            launchSingleTop = false
            popUpTo(R.id.firstFragment) {
                inclusive = true
            }
            anim {
                enter = android.R.anim.slide_in_left
                exit = android.R.anim.slide_out_right
                popEnter = android.R.anim.slide_out_right
                exit = android.R.anim.slide_in_left
            }
        }

        binding?.run {
            btnA.setOnClickListener {
                controller.navigate(R.id.action_firstFragment_to_AFragment, null, options)
            }
            btnB.setOnClickListener {
                controller.navigate(R.id.action_firstFragment_to_BFragment, null, options)
            }
            btnC.setOnClickListener {
                controller.navigate(R.id.action_firstFragment_to_CFragment, null, options)
            }
            btnD.setOnClickListener {
                controller.navigate(R.id.action_firstFragment_to_DFragment, null, options)
            }

        }
    }

}