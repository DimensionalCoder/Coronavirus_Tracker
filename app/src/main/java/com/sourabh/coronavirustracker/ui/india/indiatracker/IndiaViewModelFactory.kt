@file:Suppress("UNCHECKED_CAST")

package com.sourabh.coronavirustracker.ui.india.indiatracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabh.coronavirustracker.repository.MainRepository

class IndiaViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndiaViewModel::class.java)) {
            return IndiaViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }

}