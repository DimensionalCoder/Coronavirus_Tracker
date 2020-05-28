package com.sourabh.coronavirustracker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorldDataModel(
    val country: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: String
) : Parcelable
