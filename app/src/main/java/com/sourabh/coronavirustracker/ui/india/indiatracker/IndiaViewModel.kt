package com.sourabh.coronavirustracker.ui.india.indiatracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourabh.coronavirustracker.model.DistrictwiseDetails
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.network.Resource
import com.sourabh.coronavirustracker.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndiaViewModel(private val repo: MainRepository) : ViewModel() {
    private val _indianStatewiseDetails = MutableLiveData<Resource<List<StatewiseDetails>>>()
    val indianStatewiseDetails: LiveData<Resource<List<StatewiseDetails>>>
        get() = _indianStatewiseDetails

    private val _indianDistrictData = MutableLiveData<List<DistrictwiseDetails>>()

    init {
        initData()
    }

    private fun initData() {
        getStateData()
        getDistrictData()
    }

    private fun getStateData() {
        _indianStatewiseDetails.value = Resource.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repo.getIndianData()

//                data.filter { it.totalConfirmed != 0 }

                _indianStatewiseDetails.postValue(Resource.SUCCESS(data))
            } catch (e: Exception) {
                _indianStatewiseDetails.postValue(Resource.FAILURE(e))
            }
        }
    }

    private fun getDistrictData() {

    }

    /**
     * Navigate to Details Fragment
     */
    private val _navigateToDetailsFragment = MutableLiveData<StatewiseDetails>()
    val navigateToIndianFragment: LiveData<StatewiseDetails>
        get() = _navigateToDetailsFragment

    fun listItemClicked(statewise: StatewiseDetails) {
        _navigateToDetailsFragment.value = statewise
    }

    fun navigationComplete() {
        _navigateToDetailsFragment.value = null
    }
}