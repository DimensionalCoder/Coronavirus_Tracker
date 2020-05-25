package com.sourabh.coronavirustracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sourabh.coronavirustracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar = binding.bar
        setSupportActionBar(bottomAppBar)

    }
}
