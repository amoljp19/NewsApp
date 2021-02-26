package com.softaai.newsapp.newsArticles.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.softaai.newsapp.data.repository.ArticleRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ArticleDetailsViewModel @AssistedInject constructor(
    articleRepository: ArticleRepository,
    @Assisted articleId: Int
) : ViewModel() {

    val article = articleRepository.getArticleById(articleId).asLiveData()

    @AssistedFactory
    interface ArticleDetailsViewModelFactory {
        fun create(articleId: Int): ArticleDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: ArticleDetailsViewModelFactory,
            articleId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(articleId) as T
            }
        }
    }
}