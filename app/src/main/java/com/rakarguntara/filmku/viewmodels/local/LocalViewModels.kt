package com.rakarguntara.filmku.viewmodels.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.repository.local.LocalRepository
import com.rakarguntara.filmku.utils.togglestate.ToggleResult
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

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _toggleResult = MutableStateFlow<ToggleResult>(ToggleResult.None)
    val toggleResult: StateFlow<ToggleResult> = _toggleResult.asStateFlow()

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

    fun checkIfFavorite(id: Int) = viewModelScope.launch {
        localRepository.getMovieDetailById(id).collect { movie ->
            _isFavorite.value = movie != null
        }
    }

    fun toggleFavorite(movie: DetailMovieResponse) = viewModelScope.launch {
        if (_isFavorite.value) {
            localRepository.deleteMovieDetailById(movie.id!!)
            _toggleResult.value = ToggleResult.Deleted
        } else {
            localRepository.insertMovieDetail(movie)
            _toggleResult.value = ToggleResult.Added
        }
        checkIfFavorite(movie.id!!)
    }

}