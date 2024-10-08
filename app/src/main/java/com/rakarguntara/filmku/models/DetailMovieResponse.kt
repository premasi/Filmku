package com.rakarguntara.filmku.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.rakarguntara.filmku.utils.typeconverters.BelongTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.CompanyTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.GenreTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.LanguagesTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.OriginTypeConverters
import com.rakarguntara.filmku.utils.typeconverters.ProductionTypeConverters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_detail_table")
@Parcelize
data class DetailMovieResponse(

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("revenue")
	val revenue: Int? = null,

	@field:SerializedName("genres")
	@TypeConverters(GenreTypeConverters::class)
	val genres: List<GenresItem>? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("production_countries")
	@TypeConverters(ProductionTypeConverters::class)
	val productionCountries: List<ProductionCountriesItem>? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("budget")
	val budget: Int? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("origin_country")
	@TypeConverters(OriginTypeConverters::class)
	val originCountry: List<String>? = null,

	@field:SerializedName("spoken_languages")
	@TypeConverters(LanguagesTypeConverters::class)
	val spokenLanguages: List<SpokenLanguagesItem>? = null,

	@field:SerializedName("production_companies")
	@TypeConverters(CompanyTypeConverters::class)
	val productionCompanies: List<ProductionCompaniesItem>? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("belongs_to_collection")
	@TypeConverters(BelongTypeConverters::class)
	val belongsToCollection: BelongsToCollection? = null,

	@field:SerializedName("tagline")
	val tagline: String? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("homepage")
	val homepage: String? = null,

	@field:SerializedName("status")
	val status: String? = null
): Parcelable

@Parcelize
data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
): Parcelable

@Parcelize
data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
): Parcelable

@Parcelize
data class BelongsToCollection(

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
): Parcelable

@Parcelize
data class SpokenLanguagesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("english_name")
	val englishName: String? = null
): Parcelable

@Parcelize
data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("name")
	val name: String? = null
): Parcelable
