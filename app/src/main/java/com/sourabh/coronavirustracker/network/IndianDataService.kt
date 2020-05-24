package com.sourabh.coronavirustracker.network

import com.sourabh.coronavirustracker.model.DistrictwiseDetails
import com.sourabh.coronavirustracker.model.IndianData
import retrofit2.http.GET

interface IndianDataService {
    @GET("data.json")
    suspend fun getIndianData(): IndianData

    @GET("v2/state_district_wise.json")
    suspend fun getDistrictsList(): List<DistrictwiseDetails>
}