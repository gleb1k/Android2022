package com.example.android2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val controller =
            (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
                .navController

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.firstFragment,
                R.id.secondFragment,
                R.id.thirdFragment,
                R.id.fourthFragment,
                R.id.fifthFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setupActionBarWithNavController(controller, appBarConfiguration)



        binding?.run {
            bnvMain.setupWithNavController(controller)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val controller = findNavController(R.id.host_fragment)
        return controller.navigateUp() || super.onSupportNavigateUp()
    }
}