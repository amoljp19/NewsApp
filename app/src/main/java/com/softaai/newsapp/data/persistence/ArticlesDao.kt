package com.softaai.newsapp.data.persistence

import androidx.room.*
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.model.Comments
import com.softaai.newsapp.model.Likes
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(articles: List<Article>?)

    @Query("DELETE FROM ${Article.TABLE_NAME}")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM ${Article.TABLE_NAME} WHERE ID = :articleId")
    fun getArticleById(articleId: Int): Flow<Article>


    @Query("SELECT * FROM ${Article.TABLE_NAME}")
    fun getAllArticles(): Flow<List<Article>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikes(likes: Likes?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComments(comments: Comments?)


    @Query("SELECT * FROM ${Likes.TABLE_NAME}")
    fun getLikes(): Flow<Likes>


    @Query("SELECT * FROM ${Comments.TABLE_NAME}")
    fun getComments(): Flow<Comments>

}
