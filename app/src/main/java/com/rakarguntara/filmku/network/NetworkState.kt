package com.rakarguntara.filmku.network

sealed class NetworkState <out R> private constructor(){
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val error: String): NetworkState<Nothing>()
    object Loading: NetworkState<Nothing>()
}