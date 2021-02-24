package com.softaai.newsapp.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(articles: List<Article>)

    @Query("DELETE FROM ${Article.TABLE_NAME}")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM ${Article.TABLE_NAME} WHERE ID = :articleId")
    fun getArticleById(articleId: Int): Flow<Article>


    @Query("SELECT * FROM ${Article.TABLE_NAME}")
    fun getAllArticles(): Flow<List<Article>>
}
