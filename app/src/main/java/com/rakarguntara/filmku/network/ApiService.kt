package com.rakarguntara.filmku.network

import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.models.NowPlayingMovieResponse
import com.rakarguntara.filmku.models.PopularMovieResponse
import com.rakarguntara.filmku.models.TopRatedMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getAllPopularMovie(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : PopularMovieResponse

    @GET("/3/movie/top_rated")
    suspend fun getAllTopRatedMovie(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : TopRatedMovieResponse

    @GET("/3/movie/now_playing")
    suspend fun getAllNowPlayingMovie(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : NowPlayingMovieResponse

}