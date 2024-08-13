package com.rakarguntara.filmku.repository.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.models.NowPlayingMovieResponse
import com.rakarguntara.filmku.models.PopularMovieResponse
import com.rakarguntara.filmku.models.TopRatedMovieResponse
import com.rakarguntara.filmku.models.UpcomingMovieResponse
import com.rakarguntara.filmku.network.ApiService
import com.rakarguntara.filmku.network.NetworkState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    fun getAllPopularMovie(page: Int): LiveData<NetworkState<PopularMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllPopularMovie(page = page)
            emit(NetworkState.Success(service))
        }catch (e: IOException) {
            emit(NetworkState.Error("No internet connection or server issue $e"))
        } catch (e: HttpException){
            emit(NetworkState.Error(e.message.toString()))
        }
    }

    fun getAllTopRatedMovie(page: Int) : LiveData<NetworkState<TopRatedMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllTopRatedMovie(page = page)
            emit(NetworkState.Success(service))
        } catch (e: IOException) {
            emit(NetworkState.Error("No internet connection or server issue $e"))
        } catch (e: HttpException){
            emit(NetworkState.Error(e.message.toString()))
        }
    }

    fun getAllNowPlayingMovie(page: Int): LiveData<NetworkState<NowPlayingMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllNowPlayingMovie(page = page)
            emit(NetworkState.Success(service))
        } catch (e: IOException) {
            emit(NetworkState.Error("No internet connection or server issue $e"))
        } catch (e: HttpException){
            emit(NetworkState.Error(e.message.toString()))
        }
    }

    fun getAllUpcomingMovie(page: Int): LiveData<NetworkState<UpcomingMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getAllUpcomingMovie(page = page)
            emit(NetworkState.Success(service))
        } catch (e: IOException) {
            emit(NetworkState.Error("No internet connection or server issue $e"))
        } catch (e: HttpException){
            emit(NetworkState.Error(e.message.toString()))
        }
    }

    fun getMovieDetail(id: Int): LiveData<NetworkState<DetailMovieResponse>> = liveData {
        emit(NetworkState.Loading)
        try {
            val service = apiService.getMovieDetail(id = id)
            emit(NetworkState.Success(service))
        } catch (e: IOException) {
            emit(NetworkState.Error("No internet connection or server issue $e"))
        } catch (e: HttpException){
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}