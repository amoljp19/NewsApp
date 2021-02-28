package com.softaai.newsapp.data.repository

import androidx.annotation.MainThread
import com.softaai.newsapp.data.network.NewsApiService
import com.softaai.newsapp.data.network.Resource
import com.softaai.newsapp.data.persistence.ArticlesDao
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.model.Comments
import com.softaai.newsapp.model.Likes
import com.softaai.newsapp.model.NewsArticleApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

interface ArticleRepository {
    fun getAllArticles(): Flow<Resource<List<Article>>>
    fun getArticleById(articleId: Int): Flow<Article>

    fun getLikesCount(likeCountUrl: String?): Flow<Resource<Likes>>
    fun getCommentsCount(commentCountUrl: String?): Flow<Resource<Comments>>
}

class DefaultArticleRepository @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val newsApiService: NewsApiService
) : ArticleRepository {

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        return object : NetworkBoundRepository<List<Article>, NewsArticleApiResponse>() {

            override suspend fun saveRemoteData(response: NewsArticleApiResponse?) =
                articlesDao.addArticles(response?.articles)

            override fun fetchFromLocal(): Flow<List<Article>> = articlesDao.getAllArticles()

            override suspend fun fetchFromRemote(): Response<NewsArticleApiResponse> =
                newsApiService.getArticles()
        }.asFlow()
    }


    @MainThread
    override fun getArticleById(articleId: Int): Flow<Article> =
        articlesDao.getArticleById(articleId).distinctUntilChanged()

    override fun getLikesCount(likeCountUrl: String?): Flow<Resource<Likes>> {
        return object : NetworkBoundRepository<Likes, Likes>() {
            override suspend fun saveRemoteData(response: Likes?) = articlesDao.addLikes(response)

            override fun fetchFromLocal(): Flow<Likes> = articlesDao.getLikes()

            override suspend fun fetchFromRemote(): Response<Likes> =
                newsApiService.getLikesCount(likeCountUrl)

        }.asFlow()
    }

    override fun getCommentsCount(commentCountUrl: String?): Flow<Resource<Comments>> {
        return object : NetworkBoundRepository<Comments, Comments>() {
            override suspend fun saveRemoteData(response: Comments?) =
                articlesDao.addComments(response)

            override fun fetchFromLocal(): Flow<Comments> = articlesDao.getComments()

            override suspend fun fetchFromRemote(): Response<Comments> =
                newsApiService.getCommentsCount(commentCountUrl)
        }.asFlow()
    }
}