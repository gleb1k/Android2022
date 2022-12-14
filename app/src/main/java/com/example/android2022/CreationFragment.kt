package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentCreateBinding

class CreationFragment : Fragment(R.layout.fragment_create) {

    private var binding: FragmentCreateBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)

    }

}