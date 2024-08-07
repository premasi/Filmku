package com.rakarguntara.filmku.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rakarguntara.filmku.models.DetailMovieResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(detailMovieResponse: DetailMovieResponse)

    @Query("SELECT * FROM movie_detail_table")
    suspend fun getAllMovieFavorite(): Flow<List<DetailMovieResponse>>

    @Query("SELECT * FROM movie_detail_table WHERE id= :id")
    suspend fun getMovieDetailById(id: Int): DetailMovieResponse

    @Query("DELETE FROM movie_detail_table WHERE id = :id")
    suspend fun deleteMovieDetailById(id: Int)
}