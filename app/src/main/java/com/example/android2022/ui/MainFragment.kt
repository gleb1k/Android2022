package com.example.android2022.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android2022.R
import com.example.android2022.data.NoteRepository
import com.example.android2022.databinding.FragmentMainBinding
import com.example.android2022.ui.recyclerView.NoteAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var repository: NoteRepository? = null
    private var adapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        repository = NoteRepository(view.context)

        binding?.run {
            //Проверка на пустоту
            showTextViewCreate()
            fab.setOnClickListener {
                showNewCreationFrag()
            }
            //Задаю адаптер
            lifecycleScope.launch {
                adapter = NoteAdapter(
                    onItemClick = ::showEditCreationFrag,
                    onDeleteItem = ::deleteNote
                )
                adapter?.submitList(repository?.getAll())
                rvNote.adapter = adapter
            }

        }
    }

    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                if(isDarkModeOn()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                true
            }
            R.id.action_delete_all -> {
                lifecycleScope.launch {
                    repository?.deleteAll()
                    adapter?.submitList(repository?.getAll())
                    showTextViewCreate()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showTextViewCreate() {
        binding?.run {
            lifecycleScope.launch {
                if (repository?.getAll()?.isEmpty() == true)
                    tvCreateFirstNote.visibility = View.VISIBLE
                else {
                    tvCreateFirstNote.visibility = View.GONE
                }
            }
        }
    }

    private fun deleteNote(id: Int) {
        lifecycleScope.launch {
            val note = repository?.get(id)
            if (note != null)
                repository?.delete(note)
            adapter?.submitList(repository?.getAll())
            showTextViewCreate()
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