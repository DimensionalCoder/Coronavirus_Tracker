package com.sourabh.coronavirustracker.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IndianData(
    @Json(name = "statewise")
    val statewise: List<StatewiseDetails>
) : Parcelable


@Parcelize
data class StatewiseDetails(

    @Json(name = "active")
    val currentlyActive: Int,

    @Json(name = "confirmed")
    val totalConfirmed: Int,

    @Json(name = "deaths")
    val deaths: Int,

    @Json(name = "recovered")
    val recovered: Int,

    @Json(name = "state")
    val stateOrUT: String,

    @Suppress("SpellCheckingInspection") @Json(name = "deltaconfirmed")
    val deltaConfirmed: Int,

    @Json(name = "deltadeaths")
    val deltaDeaths: Int,

    @Json(name = "deltarecovered")
    val deltaRecovered: Int,

    @Json(name = "lastupdatedtime")
    val lastUpdateTime: String,

    @Json(name = "statenotes")
    val stateNotes: String

) : Parcelable

/**
 * District wise data. Not included in Statewise since this is a separate query with different data.
 */
@Parcelize
data class DistrictwiseDetails(
    val state: String,
    val districtData: List<Districts>
) : Parcelable

@Parcelize
data class Districts(
    val district: String,
    var notes: String?,
    val active: Int,
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int,
    val delta: Delta
) : Parcelable

@Parcelize
data class Delta(
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int
) : Parcelable