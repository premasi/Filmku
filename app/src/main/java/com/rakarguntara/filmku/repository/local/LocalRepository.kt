package com.rakarguntara.filmku.repository.local

import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.room.dao.MovieFavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(private val movieFavoriteDao: MovieFavoriteDao) {
    fun getAllMovieFavorite(): Flow<List<DetailMovieResponse>> = movieFavoriteDao.getAllMovieFavorite()
    suspend fun insertMovieDetail(detailMovieResponse: DetailMovieResponse) = movieFavoriteDao.insertMovieDetail(detailMovieResponse)
    fun getMovieDetailById(id: Int): Flow<DetailMovieResponse?> = movieFavoriteDao.getMovieDetailById(id)
    suspend fun deleteMovieDetailById(id: Int) = movieFavoriteDao.deleteMovieDetailById(id)
}