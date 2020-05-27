package com.sourabh.coronavirustracker.ui.india.indiadetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sourabh.coronavirustracker.model.Districts
import com.sourabh.coronavirustracker.model.StatewiseDetails

class IndianDetailsViewModel(
    private val statewiseDetails: StatewiseDetails,
    private val districts: List<Districts>
) : ViewModel() {

    private val _listOfData = MutableLiveData<List<Any>>()
    val listOfData: LiveData<List<Any>>
        get() = _listOfData

    init {
        createListOfHeaderAndList()
    }

    private fun createListOfHeaderAndList() {
        val list = ArrayList<Any>()
        list.add(statewiseDetails)
        val data = districts.filter { it.confirmed != 0 || it.recovered != 0 || it.deceased != 0 }
            .sortedByDescending { it.confirmed }
        list.addAll(data)
        _listOfData.value = list
    }
}