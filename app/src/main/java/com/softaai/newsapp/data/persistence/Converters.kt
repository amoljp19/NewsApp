package com.softaai.newsapp.data.persistence

import androidx.room.TypeConverter
import com.softaai.newsapp.model.Source
import com.squareup.moshi.Moshi

class Converters {

    @TypeConverter
    fun fromStringToSource(value: String): Source? =
        Moshi.Builder().build().adapter(Source::class.java).fromJson(value)

    @TypeConverter
    fun fromSourceToString(source: Source?): String =
        Moshi.Builder().build().adapter(Source::class.java).toJson(source)
}