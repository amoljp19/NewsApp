package com.softaai.newsapp.newsArticles.main

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.softaai.newsapp.R
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.databinding.ActivityMainBinding
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.newsArticles.base.BaseActivity
import com.softaai.newsapp.newsArticles.details.ArticleDetailsActivity
import com.softaai.newsapp.newsArticles.main.adapter.ArticleListAdapter
import com.softaai.newsapp.utils.NetworkUtils
import com.softaai.newsapp.utils.getColorRes
import com.softaai.newsapp.utils.show
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
        observeArticles()
        handleNetworkChanges()
    }

    fun initView() {
        mViewBinding.run {
            articleRecyclerView.adapter = mAdapter
            swipeRefreshLayout.setOnRefreshListener { getArticles() }
        }

        mViewModel.articlesLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getArticles()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }


    private fun onItemClicked(article: Article, imageView: ImageView) {
        val articleId = article.id ?: run {
            Toast.makeText(applicationContext, "Unable to launch details", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = ArticleDetailsActivity.getStartIntent(this, articleId)
        startActivity(intent)
    }

    private fun getArticles() = mViewModel.getArticles()

    private fun observeArticles() {
        mViewModel.articlesLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mViewModel.articlesLiveData.value is State.Error || mAdapter.itemCount == 0) {
                    getArticles()
                }
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                }
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}