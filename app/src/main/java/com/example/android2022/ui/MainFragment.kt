package com.example.android2022.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android2022.R
import com.example.android2022.data.Note
import com.example.android2022.data.NoteRepository
import com.example.android2022.databinding.FragmentMainBinding
import com.example.android2022.ui.recyclerView.NoteAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var repository: NoteRepository? = null
    private var adapter: NoteAdapter? = null
    private var noteList: List<Note>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        repository = NoteRepository(view.context)
        lifecycleScope.launch {
            val temp = lifecycleScope.async {
                repository?.getAll()
            }
            noteList = temp.await()
            binding?.run {
                if (noteList == null || noteList?.isEmpty() == true)
                    tvCreateFirstNote.visibility = View.VISIBLE
                else {
                    tvCreateFirstNote.visibility = View.GONE
                }
                var temp2 = noteList!![0]
            }
        }

        binding?.run {
            fab.setOnClickListener {
                showNewCreationFrag()
            }

            //Задаю адаптер
            adapter = NoteAdapter(onItemClick = ::showEditCreationFrag)
            adapter?.submitList(noteList)
//            lifecycleScope.launch {
//                adapter?.submitList(repository?.getAll())
//            }
            rvNote.adapter = adapter

            //Проверка на пустоту
//            if (noteList == null || noteList?.isEmpty() == true)
//                tvCreateFirstNote.visibility = View.VISIBLE
//            else {
//                tvCreateFirstNote.visibility = View.GONE
//            }
        }
    }

    private fun showEditCreationFrag(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                CreationFragment.getInstance(id.toString()),
                CreationFragment.CREATION_FRAGMENT_TAG
            )
            .addToBackStack("tag")
            .commit()
    }

    private fun showNewCreationFrag() {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                CreationFragment()
            )
            .addToBackStack("tag")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
    }

    companion object {
        private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

        fun getInstance(message: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(MAIN_FRAGMENT_TAG, message)
            }
        }
    }
}