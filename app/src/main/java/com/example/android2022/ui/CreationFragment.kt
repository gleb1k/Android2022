package com.example.android2022.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android2022.R
import com.example.android2022.data.Note
import com.example.android2022.data.NoteRepository
import com.example.android2022.databinding.FragmentCreateBinding
import kotlinx.coroutines.launch

class CreationFragment : Fragment(R.layout.fragment_create) {

    private var binding: FragmentCreateBinding? = null
    private var repository: NoteRepository? = null
    private var id: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)
        repository = NoteRepository(view.context)

        id = arguments?.getString(CREATION_FRAGMENT_TAG)?.toInt()

        //если редактировать
        if (id != null) {
            lifecycleScope.launch {
                val note = repository?.get(id!!)
                binding?.run {
                    etTitle.setText(note?.title)
                    etDescription.setText(note?.description)
                    btnSave.text = "Обновить"
                    btnSave.setOnClickListener {
                        update()
                        navigateToMainFrag()
                    }
                }
            }
        }
        else {
            binding?.run {
                btnSave.text = "Сохранить"
                btnSave.setOnClickListener {
                    save()
                    navigateToMainFrag()
                }
            }
        }
    }

    private fun navigateToMainFrag() {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment()
            )
            .commit()
    }

    private fun update(){
        binding?.run {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()

            lifecycleScope.launch {
                id?.let{
                    repository?.update(Note(it, title, description))
                }
            }
        }
    }

    private fun save() {
        binding?.run {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()

            lifecycleScope.launch {
                repository?.save(Note(0, title, description))
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val CREATION_FRAGMENT_TAG = "CREATION_FRAGMENT_TAG"

        fun getInstance(message: String) = CreationFragment().apply {
            arguments = Bundle().apply {
                putString(CREATION_FRAGMENT_TAG, message)
            }
        }
    }
}