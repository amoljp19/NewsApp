package com.softaai.newsapp.newsArticles.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.softaai.newsapp.databinding.ActivityArticleDetailsBinding
import com.softaai.newsapp.newsArticles.base.BaseActivity
import javax.inject.Inject


class ArticleDetailsActivity : BaseActivity<ArticleDetailsViewModel, ActivityArticleDetailsBinding>() {

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
    }

    private fun initArticle() {
        mViewModel.article.observe(this) { article ->
            mViewBinding.articleContent.apply {
                articleTitle.text = article.title
                articleAuthor.text = article.author
                articleDescription.text = article.description
            }
            mViewBinding.imageView.load(article.urlToImage)
        }
    }



    override fun getViewBinding(): ActivityArticleDetailsBinding =
        ActivityArticleDetailsBinding.inflate(layoutInflater)


    companion object {
        private const val KEY_ARTICLE_ID = "articleId"

        fun getStartIntent(
            context: Context,
            articleId: Int
        ) = Intent(context, ArticleDetailsActivity::class.java).apply { putExtra(KEY_ARTICLE_ID, articleId) }
    }
}
