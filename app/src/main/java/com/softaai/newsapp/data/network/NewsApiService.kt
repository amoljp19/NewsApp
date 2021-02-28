package com.softaai.newsapp.data.network

import com.softaai.newsapp.model.Comments
import com.softaai.newsapp.model.Likes
import com.softaai.newsapp.model.NewsArticleApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsApiService {
    @GET("/v2/top-headlines?country=us")
    suspend fun getArticles(): Response<NewsArticleApiResponse>

    @GET
    suspend fun getLikesCount(@Url likeCountUrl: String?): Response<Likes>

    @GET
    suspend fun getCommentsCount(@Url commentsCountUrl: String?): Response<Comments>


    companion object {
        const val NEWS_API_URL = "https://newsapi.org"
    }
}