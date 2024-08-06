package com.rakarguntara.filmku.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rakarguntara.filmku.models.ResultsItem
import com.rakarguntara.filmku.network.ApiService
import com.rakarguntara.filmku.network.NetworkState
import javax.inject.Inject


class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    fun getAllPopularMovie(page: Int): LiveData<NetworkState<List<ResultsItem>>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllMoviePopular(page = page)
            emit(NetworkState.Success(service))
        } catch (e: Exception){
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}