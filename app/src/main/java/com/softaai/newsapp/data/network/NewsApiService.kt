package com.softaai.newsapp.data.network

import com.softaai.newsapp.model.Article
import com.softaai.newsapp.model.NewsArticleApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
        @GET("/v2/top-headlines?country=us")
        suspend fun getArticles(): Response<NewsArticleApiResponse>

        companion object {
            const val NEWS_API_URL = "https://newsapi.org"
        }
}