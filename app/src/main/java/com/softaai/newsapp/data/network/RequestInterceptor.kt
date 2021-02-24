package com.softaai.newsapp.data.network

import com.softaai.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
        val request = originalRequest.newBuilder().url(originalUrl).build()
        return chain.proceed(request)
    }
}