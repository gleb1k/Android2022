package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.android2022.databinding.FragmentAddDialogBinding

class AddDialogFragment() : DialogFragment() {

    private var binding: FragmentAddDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddDialogBinding.bind(view)

    }
}