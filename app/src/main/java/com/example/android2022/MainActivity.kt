package com.example.android2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if(savedInstanceState !=null) {
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment()
            )

            .commit()
    }
}