package com.example.android2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val binding = binding!!
    }
}