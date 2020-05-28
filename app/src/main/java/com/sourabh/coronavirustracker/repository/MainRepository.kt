package com.sourabh.coronavirustracker.repository

import com.sourabh.coronavirustracker.model.DistrictwiseDetails
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.model.WorldDataModel
import com.sourabh.coronavirustracker.network.IndianDataService
import com.sourabh.coronavirustracker.network.WorldDataService

/**
 * Default constructor values set to null
 */
class MainRepository(
    private val indianDataService: IndianDataService? = null,
    private val worldDataService: WorldDataService? = null
) {

    /**
     * These are non-null since the non-null value will be passed to the constructor
     */
    suspend fun getIndianStateData(): List<StatewiseDetails> =
        indianDataService?.getIndianData()?.statewise!!

    suspend fun getDistrictData(): List<DistrictwiseDetails> =
        indianDataService?.getDistrictsList()!!

    suspend fun getWorldData(): List<WorldDataModel> = worldDataService?.getData()!!
}