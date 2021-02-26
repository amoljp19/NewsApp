package com.softaai.newsapp.newsArticles.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.softaai.newsapp.R
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.databinding.ActivityMainBinding
import com.softaai.newsapp.newsArticles.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)



    }

    override fun onStart() {
        super.onStart()
        getArticles()
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

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}