package com.example.android2022.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android2022.R
import com.example.android2022.databinding.FragmentAddDialogBinding
import com.example.android2022.model.MainItem
import com.example.android2022.model.Repository

class AddDialogFragment(
    val actionAdd: (Int, MainItem.Car) -> Unit
) : DialogFragment(R.layout.fragment_add_dialog) {

    private var binding: FragmentAddDialogBinding? = null

    private var title: String = ""
    private var description: String = ""
    private var position: Int = Repository.dataList.lastIndex + 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddDialogBinding.bind(view)

        binding?.run {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setOnClickListener {
                title = tied1.text.toString()

                description = tied2.text.toString()

                if (!tied3.text.isNullOrEmpty())
                    position = tied3.text.toString().toInt()

                if (position > Repository.dataList.lastIndex || position <= -1)
                    position = Repository.dataList.lastIndex + 1

                //костыль
                val newCar = MainItem.Car(title,description, "", "https://sun9-58.userapi.com/impg/-NbYB0FPlbIw65oOc6StypNuIsFSd9oyznnbWg/roOJ4sSN18s.jpg?size=768x768&quality=95&sign=019f7f22e7a4bee1334e342740796bd4&type=album")

                actionAdd(position, newCar)

                dismiss()

            }
        }
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

//        fun getInstance(text: String) =
//            AddDialogFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ADD_DIALOG_TAG, text)
//                }
//            }
    }
}