package com.softaai.newsapp.data.repository

import androidx.annotation.MainThread
import com.softaai.newsapp.data.network.NewsApiService
import com.softaai.newsapp.data.network.Resource
import com.softaai.newsapp.data.persistence.ArticlesDao
import com.softaai.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

interface ArticleRepository {
    fun getAllArticles(): Flow<Resource<List<Article>>>
    fun getArticleById(articleId: Int): Flow<Article>
}

class DefaultArticleRepository @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val newsApiService: NewsApiService
) : ArticleRepository {

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        return object : NetworkBoundRepository<List<Article>, List<Article>>() {

            override suspend fun saveRemoteData(response: List<Article>) = articlesDao.addArticles(response)

            override fun fetchFromLocal(): Flow<List<Article>> = articlesDao.getAllArticles()

            override suspend fun fetchFromRemote(): Response<List<Article>> = newsApiService.getArticles()
        }.asFlow()
    }


    @MainThread
    override fun getArticleById(articleId: Int): Flow<Article> = articlesDao.getArticleById(articleId).distinctUntilChanged()
}