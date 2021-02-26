package com.softaai.newsapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softaai.newsapp.model.Article.Companion.TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = TABLE_NAME)
@JsonClass(generateAdapter = true)
data class Article(
    @PrimaryKey
    var id: Int? = 0,
    @Json(name = "author")
    val author: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "source")
    val source: Source?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "urlToImage")
    val urlToImage: String?
){
    companion object {
        const val TABLE_NAME = "news_articles"
    }
}