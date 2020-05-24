package com.sourabh.coronavirustracker.repository

import com.sourabh.coronavirustracker.network.IndianDataService

class MainRepository(private val indianDataService: IndianDataService) {

    suspend fun getIndianData() = indianDataService.getIndianData().statewise
    suspend fun getDistrictData() = indianDataService.getDistrictsList()
}