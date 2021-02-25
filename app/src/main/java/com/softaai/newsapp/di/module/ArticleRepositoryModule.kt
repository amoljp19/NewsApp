package com.softaai.newsapp.di.module

import com.softaai.newsapp.data.repository.ArticleRepository
import com.softaai.newsapp.data.repository.DefaultArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ArticleRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindArticleRepository(repository: DefaultArticleRepository): ArticleRepository
}