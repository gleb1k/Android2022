package com.example.android2022

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.android2022.databinding.FragmentDialogBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomDialogFragment(
    private val counterValue: Int,
    val saveCounter: (Int) -> Unit
) : DialogFragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog, null, false)
        val editText = view.findViewById<TextInputEditText>(R.id.textInputEditText)
        val textInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        textInputLayout.isErrorEnabled = true

        return AlertDialog.Builder(requireContext())
            .setTitle("Change counter value")
            .setView(view)
            .setPositiveButton("PLUS") { dialog, _ ->
                if (valueIsCorrect(editText)) {
                    saveCounter(counterValue + editText.text.toString().toInt())
                } else {
                    //почему-то ошибка не выводится(
                    textInputLayout.error = "Не верный формат данных"
                    //пока что так
                    Toast.makeText(context,"Не верный формат данных ", Toast.LENGTH_SHORT ).show()
                }
            }
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("MINUS") { dialog, _ ->
                if (valueIsCorrect(editText) && counterValue - editText.text.toString().toInt() >= 0) {
                    saveCounter(counterValue - editText.text.toString().toInt())
                } else {
                    //почему-то ошибка не выводится(
                    textInputLayout.error = "Не верный формат данных"
                    //пока что так
                    Toast.makeText(context,"Не верный формат данных", Toast.LENGTH_SHORT).show()
                }
            }
            .create()
    }

    private fun valueIsCorrect(editText: TextInputEditText): Boolean {
        if (!editText.text.isNullOrBlank()) {
            return (editText.text.toString().toInt() in 0..100)
        }
        return false
    }

}