package com.sourabh.coronavirustracker.repository

import com.sourabh.coronavirustracker.model.DistrictwiseDetails
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.model.WorldDataModel
import com.sourabh.coronavirustracker.network.IndianDataService
import com.sourabh.coronavirustracker.network.WorldDataService

class MainRepository {
    private lateinit var indianDataService: IndianDataService
    private lateinit var worldDataService: WorldDataService

    suspend fun getIndianStateData(): List<StatewiseDetails> = indianDataService.getIndianData().statewise

    suspend fun getDistrictData(): List<DistrictwiseDetails> = indianDataService.getDistrictsList()

    suspend fun getWorldData(): List<WorldDataModel> = worldDataService.getData()

    fun setIndianDataService(indianDataService: IndianDataService) {
        this.indianDataService = indianDataService
    }

    fun setWorldDataService(worldDataService: WorldDataService) {
        this.worldDataService = worldDataService
    }
}