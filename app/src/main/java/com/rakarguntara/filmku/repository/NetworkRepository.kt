package com.rakarguntara.filmku.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rakarguntara.filmku.models.PopularMovieResponse
import com.rakarguntara.filmku.models.TopRatedMovieResponse
import com.rakarguntara.filmku.network.ApiService
import com.rakarguntara.filmku.network.NetworkState
import javax.inject.Inject


class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    fun getAllPopularMovie(page: Int): LiveData<NetworkState<PopularMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllPopularMovie(page = page)
            emit(NetworkState.Success(service))
        } catch (e: Exception){
            emit(NetworkState.Error(e.message.toString()))
        }
    }

    fun getAllTopRatedMovie(page: Int) : LiveData<NetworkState<TopRatedMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllTopRatedMovie(page = page)
            emit(NetworkState.Success(service))
        } catch (e: Exception){
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}