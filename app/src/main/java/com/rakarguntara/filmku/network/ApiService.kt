package com.rakarguntara.filmku.network

import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.models.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getAllMoviePopular(
        @Header("Authorization") auth: String = BuildConfig.AUTH,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : PopularMovieResponse

}