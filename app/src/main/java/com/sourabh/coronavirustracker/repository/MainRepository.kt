package com.sourabh.coronavirustracker.repository

import com.sourabh.coronavirustracker.model.DistrictwiseDetails
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.network.IndianDataService

class MainRepository(private val indianDataService: IndianDataService) {

    suspend fun getIndianStateData(): List<StatewiseDetails> =
        indianDataService.getIndianData().statewise

    suspend fun getDistrictData(): List<DistrictwiseDetails> =
        indianDataService.getDistrictsList()
}