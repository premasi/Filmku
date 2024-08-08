package com.rakarguntara.filmku.viewmodels.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.repository.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModels @Inject constructor(private val localRepository: LocalRepository):ViewModel() {
    private val _movieList = MutableStateFlow<List<DetailMovieResponse>>(emptyList())
    val movieList = _movieList.asStateFlow()

    private val _movie = MutableStateFlow<DetailMovieResponse?>(null)
    val movie: StateFlow<DetailMovieResponse?> = _movie.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.getAllMovieFavorite().distinctUntilChanged()
                .collect{list->
                    if(list.isNotEmpty()){
                        _movieList.value = list
                    }
                }
        }
    }

    fun getMovieDetailById(id: Int) = viewModelScope.launch {
        _movie.value = localRepository.getMovieDetailById(id)
    }

    fun insertMovieDetail(detailMovieResponse: DetailMovieResponse) = viewModelScope.launch {
        localRepository.insertMovieDetail(detailMovieResponse)
    }

    fun deleteMovieById(id: Int) = viewModelScope.launch {
        localRepository.deleteMovieDetailById(id)
    }

}