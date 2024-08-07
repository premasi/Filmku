package com.rakarguntara.filmku.network

import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.models.NowPlayingMovieResponse
import com.rakarguntara.filmku.models.PopularMovieResponse
import com.rakarguntara.filmku.models.TopRatedMovieResponse
import com.rakarguntara.filmku.models.UpcomingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

    @GET("/3/movie/upcoming")
    suspend fun getAllUpcomingMovie(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : UpcomingMovieResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
    ) : DetailMovieResponse
}