package com.rakarguntara.filmku.viewmodels

import androidx.lifecycle.ViewModel
import com.rakarguntara.filmku.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetalViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {
    fun getMovieDetail(id: Int) = networkRepository.getMovieDetail(id)
}