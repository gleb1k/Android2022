package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentMainBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)
    }
}