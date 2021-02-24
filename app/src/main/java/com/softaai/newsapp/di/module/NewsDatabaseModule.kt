package com.softaai.newsapp.di.module

import android.app.Application
import com.softaai.newsapp.data.persistence.NewsArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = NewsArticlesDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideArticlesDao(database: NewsArticlesDatabase) = database.getArticlesDao()
}