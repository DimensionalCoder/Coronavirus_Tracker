package com.sourabh.coronavirustracker.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sourabh.coronavirustracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar = binding.bar
        setSupportActionBar(bottomAppBar)
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
        }else {
            super.attachBaseContext(newBase)
        }
    }

}