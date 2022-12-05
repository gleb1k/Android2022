package com.example.android2022

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var binder: MusicService.MusicBinder? = null


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? MusicService.MusicBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this@MainActivity, MusicService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
        MusicNotification(this@MainActivity).createChannel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if (savedInstanceState != null) {
            return
        }


        //некрасиво и ладно
        if (intent.action == MusicNotification.SHOW_INFO_FRAGMENT) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    MainFragment.newInstance("ShowInfoFragment"),
                    MainFragment.MAIN_FRAGMENT_TAG
                )
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    MainFragment()
                )
                .commit()
        }

    }

    override fun onDestroy() {
        binder = null
        binding = null
        MusicNotification(this@MainActivity).closeNotification()
        super.onDestroy()
    }
}