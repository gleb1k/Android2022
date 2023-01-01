package com.example.android2022.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            val temp = loadFlag()
            when (loadFlag()) {
                GRID_FLAG -> rvNote.layoutManager = GridLayoutManager(context, 2).also {
                    setAdapter(it)
                }
                LINEAR_FLAG -> rvNote.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                ).also {
                    setAdapter(it)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                if (isDarkModeOn()) {
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
            R.id.action_layout_manager -> {
                binding?.run {
                    when (rvNote.layoutManager) {
                        is GridLayoutManager ->
                            rvNote.layoutManager = LinearLayoutManager(
                                context,
                                LinearLayoutManager.VERTICAL,
                                false
                            ).also {
                                setAdapter(it)
                                saveFlag(LINEAR_FLAG)
                            }
                        is LinearLayoutManager ->
                            rvNote.layoutManager = GridLayoutManager(context, 2).also {
                                setAdapter(it)
                                saveFlag(GRID_FLAG)
                            }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFlag(): String {
        val sharedPreference =
            requireActivity().getSharedPreferences("LayoutManagerSP", Context.MODE_PRIVATE)
        return sharedPreference.getString(RECYCLER_VIEW_KEY, GRID_FLAG) ?: GRID_FLAG
    }

    private fun saveFlag(flag: String) {
        val sharedPreference =
            requireActivity().getSharedPreferences("LayoutManagerSP", Context.MODE_PRIVATE)
        sharedPreference.edit().apply {
            putString(RECYCLER_VIEW_KEY, flag)
        }.apply()
    }


    private fun setAdapter(layoutManager: RecyclerView.LayoutManager) {
        binding?.run {
            lifecycleScope.launch {
                adapter = NoteAdapter(
                    layoutManager = layoutManager,
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
        private const val RECYCLER_VIEW_KEY = "RECYCLER_VIEW_KEY"
        private const val GRID_FLAG = "GRID_FLAG"
        private const val LINEAR_FLAG = "LINEAR_FLAG"

        private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

        fun getInstance(message: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(MAIN_FRAGMENT_TAG, message)
            }
        }
    }
}