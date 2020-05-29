package com.sourabh.coronavirustracker.ui.world

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourabh.coronavirustracker.model.WorldDataModel
import com.sourabh.coronavirustracker.network.Resource
import com.sourabh.coronavirustracker.repository.MainRepository
import kotlinx.coroutines.launch

class WorldViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _worldData = MutableLiveData<Resource<List<WorldDataModel>>>()
    val worldData: LiveData<Resource<List<WorldDataModel>>>
        get() = _worldData

    init {
        getWorldData()
    }

    fun retry() {
        getWorldData()
    }

    private fun getWorldData() {
        _worldData.value = Resource.LOADING
        viewModelScope.launch {
            try {
                _worldData.postValue(Resource.SUCCESS(mainRepository.getWorldData()))
            } catch (e: Exception) {
                _worldData.postValue(Resource.FAILURE(e))
            }
        }
    }
}