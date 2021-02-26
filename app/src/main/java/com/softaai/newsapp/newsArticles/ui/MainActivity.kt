package com.softaai.newsapp.newsArticles.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.multidex.BuildConfig
import com.softaai.newsapp.R
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.newsArticles.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.articlesLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getArticles()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        mViewModel.articlesLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getArticles()
            }
        }
        observeArticles()
    }

    private fun getArticles() = mViewModel.getArticles()

    private fun observeArticles() {
        mViewModel.articlesLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(applicationContext, "Loading...", Toast.LENGTH_SHORT).show()
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}