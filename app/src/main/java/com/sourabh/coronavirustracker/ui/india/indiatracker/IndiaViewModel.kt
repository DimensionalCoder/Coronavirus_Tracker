package com.sourabh.coronavirustracker.ui.india.indiatracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourabh.coronavirustracker.model.Districts
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

        _indianStatewiseDetails.value = Resource.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            val (states, districtData) = getIndianData()
            _indianStatewiseDetails.postValue(states)
            _indianDistrictData.postValue(districtData)
        }
    }

    private suspend fun getIndianData(): Pair<Resource<List<StatewiseDetails>>, List<DistrictwiseDetails>> {
        return try {
            Log.i("IndiaViewModel", "Recalled")
            val states = getStateData().filter { it.totalConfirmed != 0 }
            val stateAndDistricts = getDistrictData()
            Resource.SUCCESS(states) to stateAndDistricts
        } catch (e: Exception) {
            Resource.FAILURE(e) to emptyList()
        }
    }

    private suspend fun getStateData(): List<StatewiseDetails> {
        return repo.getIndianStateData()
    }

    private suspend fun getDistrictData(): List<DistrictwiseDetails> {
        return repo.getDistrictData()
    }

    /**
     * Navigate to Details Fragment
     */
    private val _navigateToDetailsFragment =
        MutableLiveData<Pair<StatewiseDetails, List<Districts>>>()

    //    private val detailsData = MutableLiveData<Pair<StatewiseDetails, DistrictwiseDetails>>()
    val navigateToIndianFragment: LiveData<Pair<StatewiseDetails, List<Districts>>>
        get() = _navigateToDetailsFragment

    fun listItemClicked(state: StatewiseDetails) {

        val listOfStateAndDistricts = _indianDistrictData.value

        viewModelScope.launch(Dispatchers.Default) {

            listOfStateAndDistricts?.let {
                val districtOfState =
                    listOfStateAndDistricts.asSequence().filter { it.state == state.stateOrUT }
                        .first()

                _navigateToDetailsFragment.postValue(state to districtOfState.districtData)
            }
        }
    }

    fun navigationComplete() {
        _navigateToDetailsFragment.value = null
    }
}