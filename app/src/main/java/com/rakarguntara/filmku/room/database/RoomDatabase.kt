package com.rakarguntara.filmku.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.room.dao.MovieFavoriteDao
import com.rakarguntara.filmku.utils.typeconverters.BelongTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.CompanyTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.GenreTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.LanguagesTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.OriginTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.ProductionTypeConverters

@Database(entities = [DetailMovieResponse::class], version = 1, exportSchema = false)
@TypeConverters(
    GenreTypeConverters::class,
    CompanyTypeConverters::class,
    OriginTypeConverters::class,
    LanguagesTypeConverters::class,
    ProductionTypeConverters::class,
    BelongTypeConverters::class
)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun movieFavDao(): MovieFavoriteDao
}