package com.softaai.newsapp.di.module

import com.softaai.newsapp.data.network.NewsApiService
import com.softaai.newsapp.data.network.RequestInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(RequestInterceptor()).build();

    
    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): NewsApiService = Retrofit.Builder()
        .baseUrl(NewsApiService.NEWS_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .client(okHttpClient)
        .build()
        .create(NewsApiService::class.java)
}
