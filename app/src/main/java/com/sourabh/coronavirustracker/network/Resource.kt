package com.sourabh.coronavirustracker.network

sealed class Resource<out T> {
    object LOADING : Resource<Nothing>()
    data class SUCCESS<out T>(val data: T) : Resource<T>()
    data class FAILURE(val e: Exception) : Resource<Nothing>()
}