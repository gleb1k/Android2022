package com.example.android2022.ui

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android2022.R
import com.example.android2022.data.Note
import com.example.android2022.data.NoteRepository
import com.example.android2022.databinding.FragmentCreateBinding
import kotlinx.coroutines.launch
import java.util.*
import java.util.jar.Manifest

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

    private fun getLocation() {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if ((ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(context!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
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
            val date = Calendar.getInstance().time

            lifecycleScope.launch {
                id?.let{
                    repository?.update(Note(it, title, description,date= date))
                }
            }
        }
    }

    private fun save() {
        binding?.run {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val date = Calendar.getInstance().time

            lifecycleScope.launch {
                repository?.save(Note(0, title, description, date = date))
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