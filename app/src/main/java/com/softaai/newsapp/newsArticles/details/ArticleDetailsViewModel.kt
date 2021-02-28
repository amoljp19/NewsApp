package com.softaai.newsapp.newsArticles.details

import androidx.lifecycle.*
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.data.repository.ArticleRepository
import com.softaai.newsapp.model.Comments
import com.softaai.newsapp.model.Likes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ArticleDetailsViewModel @AssistedInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted articleId: Int
) : ViewModel() {

    private val _likesLiveData = MutableLiveData<State<Likes>>()
    val likesLiveData: LiveData<State<Likes>> = _likesLiveData

    private val _commentsLiveData = MutableLiveData<State<Comments>>()
    val commentsLiveData: LiveData<State<Comments>> = _commentsLiveData

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


    fun getLikesCount(likeCountUrl: String?) {

        viewModelScope.launch {
            articleRepository.getLikesCount(likeCountUrl)
                .onStart { _likesLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _likesLiveData.value = state }
        }
    }

    fun getCommentsCount(commentCountUrl: String?) {
        viewModelScope.launch {
            articleRepository.getCommentsCount(commentCountUrl)
                .onStart { _commentsLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _commentsLiveData.value = state }
        }
    }

}