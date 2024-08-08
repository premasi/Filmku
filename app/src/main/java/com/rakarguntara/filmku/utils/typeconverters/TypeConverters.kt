package com.rakarguntara.filmku.utils.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rakarguntara.filmku.models.BelongsToCollection
import com.rakarguntara.filmku.models.GenresItem
import com.rakarguntara.filmku.models.ProductionCompaniesItem
import com.rakarguntara.filmku.models.ProductionCountriesItem
import com.rakarguntara.filmku.models.SpokenLanguagesItem

class GenreTypeConverters {
    @TypeConverter
    fun fromList(genre: List<GenresItem>?): String? {
        return genre?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(genreJson: String?): List<GenresItem>? {
        val type = object : TypeToken<List<GenresItem>>() {}.type
        return genreJson?.let { Gson().fromJson(it, type) }
    }
}

class CompanyTypeConverters{
    @TypeConverter
    fun fromList(companies: List<ProductionCompaniesItem>?): String? {
        return companies?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(companiesJson: String?): List<ProductionCompaniesItem>?{
        val type = object : TypeToken<List<ProductionCompaniesItem>>() {}.type
        return companiesJson?.let { Gson().fromJson(it, type) }
    }
}

class OriginTypeConverters{
    @TypeConverter
    fun fromList(string: List<String>?): String? {
        return string?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(string: String?): List<String>? {
        val type = object : TypeToken<List<String>>(){}.type
        return string?.let { Gson().fromJson(it, type) }
    }
}

class LanguagesTypeConverters{
    @TypeConverter
    fun fromList(list: List<SpokenLanguagesItem>?) : String?{
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(list: String?): List<SpokenLanguagesItem>? {
        val type = object : TypeToken<List<SpokenLanguagesItem>>(){}.type
        return list?.let { Gson().fromJson(it, type) }
    }
}

class ProductionTypeConverters{
    @TypeConverter
    fun fromList(list: List<ProductionCountriesItem>?): String?{
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(list: String?): List<ProductionCountriesItem>?{
        val type = object : TypeToken<List<ProductionCountriesItem>>(){}.type
        return list?.let { Gson().fromJson(it, type) }
    }
}

class BelongTypeConverters {
    @TypeConverter
    fun toJson(belongsToCollection: BelongsToCollection?): String? {
        return belongsToCollection?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun fromJson(belongsToCollectionJson: String?): BelongsToCollection? {
        val type = object : TypeToken<BelongsToCollection>() {}.type
        return belongsToCollectionJson?.let { Gson().fromJson(it, type) }
    }
}
