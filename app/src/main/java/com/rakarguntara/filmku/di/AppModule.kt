package com.rakarguntara.filmku.di

import android.content.Context
import androidx.room.Room
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.network.ApiService
import com.rakarguntara.filmku.room.dao.MovieFavoriteDao
import com.rakarguntara.filmku.room.database.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMovieFavoriteDao(roomDatabase: RoomDatabase): MovieFavoriteDao =
        roomDatabase.movieFavDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase =
        Room.databaseBuilder(context, RoomDatabase::class.java, "room_database")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideApiService(): ApiService{
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).client(client).build()

        return retrofit.create(ApiService::class.java)
    }

}