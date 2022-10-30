package com.example.android2022.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android2022.R
import com.example.android2022.databinding.FragmentAddDialogBinding

class AddDialogFragment() : DialogFragment(R.layout.fragment_add_dialog) {

    private var binding: FragmentAddDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddDialogBinding.bind(view)

    }

    //Задает ширину диалог фрагмента
    override fun onStart() {
        super.onStart()
        val metrics: DisplayMetrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog?.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        const val ADD_DIALOG_TAG = "ADD_DIALOG_TAG"
    }
}