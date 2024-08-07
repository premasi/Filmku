package com.rakarguntara.filmku.viewmodels

import androidx.lifecycle.ViewModel
import com.rakarguntara.filmku.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {
    fun getAllPopularMovie(page: Int) = repository.getAllPopularMovie(page)

    fun getAllTopRatedMovie(page: Int) = repository.getAllTopRatedMovie(page)

    fun getAllNowPlayingMovie(page: Int) = repository.getAllNowPlayingMovie(page)

    fun getAllUpcomingMovie(page: Int) = repository.getAllUpcomingMovie(page)

    fun getMovieDetail(id: Int) = repository.getMovieDetail(id)
}