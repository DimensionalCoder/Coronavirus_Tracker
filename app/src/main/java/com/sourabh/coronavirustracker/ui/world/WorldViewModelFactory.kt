package com.sourabh.coronavirustracker.ui.world

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabh.coronavirustracker.repository.MainRepository

@Suppress("UNCHECKED_CAST")
class WorldViewModelFactory(private val mainRepository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorldViewModel::class.java)) {
            return WorldViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }

}