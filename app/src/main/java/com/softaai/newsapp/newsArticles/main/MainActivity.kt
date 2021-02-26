package com.softaai.newsapp.newsArticles.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.databinding.ActivityMainBinding
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.newsArticles.base.BaseActivity
import com.softaai.newsapp.newsArticles.main.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()

    private val mAdapter = ArticleListAdapter(this::onItemClicked)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        initView()
    }

    override fun onStart() {
        super.onStart()
        getArticles()
        observeArticles()
    }

    fun initView() {
        mViewBinding.run {
            articleRecyclerView.adapter = mAdapter
        }
    }


    private fun onItemClicked(article: Article, imageView: ImageView) {
        TODO("Not yet implemented")
    }

    private fun getArticles() = mViewModel.getArticles()

    private fun observeArticles() {
        mViewModel.articlesLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(applicationContext, "Loading...", Toast.LENGTH_SHORT).show()
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                        mAdapter.submitList(state.data.toMutableList())
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