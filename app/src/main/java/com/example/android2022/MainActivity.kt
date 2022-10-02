package com.example.android2022

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.hide();

        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

        binding?.run{
            btn1.setOnClickListener(){
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("geo:55.79, 49.12")
                }
                    startActivity(intent)
            }
            btn2.setOnClickListener(){
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(takePictureIntent)
            }
            btn3.setOnClickListener(){
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:8(937)4894450")
                }
                    startActivity(intent)

            }
        }
    }
}

