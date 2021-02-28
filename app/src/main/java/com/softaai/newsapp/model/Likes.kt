package com.softaai.newsapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = Likes.TABLE_NAME)
@JsonClass(generateAdapter = true)
data class Likes(
    @PrimaryKey
    @Json(name = "likes")
    val likes: Int?
){
    companion object {
        const val TABLE_NAME = "article_likes"
    }
}