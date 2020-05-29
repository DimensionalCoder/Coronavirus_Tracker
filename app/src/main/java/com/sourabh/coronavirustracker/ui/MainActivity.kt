package com.sourabh.coronavirustracker.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar = binding.bar
        setSupportActionBar(bottomAppBar)

        /**
         * Open Navigation Drawer
         */
        bottomAppBar.setNavigationOnClickListener {
            val bottomNavDrawer = BottomNavigationDrawer.getInstance()

            bottomNavDrawer.show(supportFragmentManager, bottomNavDrawer.tag)

        }

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val title = binding.title
            when (destination.id) {
                R.id.indiaFragment -> title.text = resources.getText(R.string.india)
                R.id.worldFragment -> title.text = resources.getText(R.string.global)
                R.id.indiaDetailsFragment -> bottomAppBar.performShow()
            }
        }

    }

    /**
     * Overriding font scale for the app
     * Since setting both System font size and display size at the same time from the settings app breaks the views
     */
    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            val newOverride = Configuration(newBase.resources?.configuration)
            newOverride.fontScale = 1.0f
            applyOverrideConfiguration(newOverride)
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(newBase)
        }
    }

}