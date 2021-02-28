package com.softaai.newsapp.newsArticles.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.softaai.newsapp.data.network.State
import com.softaai.newsapp.databinding.ActivityArticleDetailsBinding
import com.softaai.newsapp.newsArticles.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleDetailsActivity :
    BaseActivity<ArticleDetailsViewModel, ActivityArticleDetailsBinding>() {

    @Inject
    lateinit var viewModelFactory: ArticleDetailsViewModel.ArticleDetailsViewModelFactory

    override val mViewModel: ArticleDetailsViewModel by viewModels {
        val articleId = intent.extras?.getInt(KEY_ARTICLE_ID)
            ?: throw IllegalArgumentException("`articleId` must be non-null")

        ArticleDetailsViewModel.provideFactory(viewModelFactory, articleId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        initArticle()

        observeLikes()
        observeComments()
    }

    private fun initArticle() {
        mViewModel.article.observe(this) { article ->
            mViewBinding.articleContent.apply {
                articleTitle.text = article.title
                articleAuthor.text = article.author
                articleDescription.text = article.description
                getLikesCount(getArticleId(article.url))
                getCommentsCount(getArticleId(article.url))
            }
            mViewBinding.imageView.load(article.urlToImage)
        }
    }

    private fun getArticleId(articleUrl: String?): String? {
        return articleUrl?.replaceFirst("https://", "")?.replace("/", "-")
    }


    private fun getLikesCount(articleId: String?) =
        mViewModel.getLikesCount("https://cn-news-info-api.herokuapp.com/likes/${articleId}")

    private fun getCommentsCount(articleId: String?) =
        mViewModel.getCommentsCount("https://cn-news-info-api.herokuapp.com/comments/${articleId}")

    private fun observeLikes() {
        mViewModel.likesLiveData.observe(this) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let {
                        if (it != null) {
                            mViewBinding.articleContent.articleLikesCount.text =
                                "Number of Likes : ${it.likes}"
                        }
                    }

                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun observeComments() {
        mViewModel.commentsLiveData.observe(this) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let {
                        if (it != null) {
                            mViewBinding.articleContent.articleCommentsCount.text =
                                "Number of Comments : ${it.comments}"
                        }
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    override fun getViewBinding(): ActivityArticleDetailsBinding =
        ActivityArticleDetailsBinding.inflate(layoutInflater)


    companion object {
        private const val KEY_ARTICLE_ID = "articleId"

        fun getStartIntent(
            context: Context,
            articleId: Int
        ) = Intent(context, ArticleDetailsActivity::class.java).apply {
            putExtra(
                KEY_ARTICLE_ID,
                articleId
            )
        }
    }
}
