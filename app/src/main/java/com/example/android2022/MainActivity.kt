package com.example.android2022

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null


    private val getContent =
        registerForActivityResult(CustomActivityResultContract(this@MainActivity)) { bm ->
            if (bm != null) {
                binding!!.iv.setImageBitmap(bm)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).takeIf {
            it.resolveActivity(this.packageManager) != null
        }

        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }

        val chooser = Intent.createChooser(galleryIntent, "Choose one")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        binding?.run {
            btn.setOnClickListener {
                when {
                    ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        getContent.launch(chooser)
                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity,
                        android.Manifest.permission.CAMERA
                    ) -> {
                        Toast.makeText(this@MainActivity, "required permission", Toast.LENGTH_LONG)
                            .show()
                        requestPermissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                        getContent.launch(chooser)
                    }
                    else -> {
                        requestPermissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                        getContent.launch(chooser)
                    }
                }
            }
        }
    }
}