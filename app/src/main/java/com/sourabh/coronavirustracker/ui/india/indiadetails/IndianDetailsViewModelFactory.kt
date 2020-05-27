package com.sourabh.coronavirustracker.ui.india.indiadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabh.coronavirustracker.model.Districts
import com.sourabh.coronavirustracker.model.StatewiseDetails

@Suppress("UNCHECKED_CAST")
class IndianDetailsViewModelFactory(
    private val statewiseDetails: StatewiseDetails,
    private val districts: List<Districts>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndianDetailsViewModel::class.java)) {
            return IndianDetailsViewModel(statewiseDetails, districts) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }

}