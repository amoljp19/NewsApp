package com.softaai.newsapp.newsArticles.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.data.repository.ArticleRepository
import com.softaai.newsapp.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    ViewModel() {

    private val _articlesLiveData = MutableLiveData<State<List<Article>>>()

    val articlesLiveData: LiveData<State<List<Article>>> = _articlesLiveData

    fun getArticles() {
        viewModelScope.launch {
            articleRepository.getAllArticles()
                .onStart { _articlesLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _articlesLiveData.value = state }
        }
    }
}


