package com.softaai.newsapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = Comments.TABLE_NAME)
@JsonClass(generateAdapter = true)
data class Comments(
    @PrimaryKey
    @Json(name = "comments")
    val comments: Int?
){
    companion object {
        const val TABLE_NAME = "article_comments"
    }
}
