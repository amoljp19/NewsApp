package com.softaai.newsapp.newsArticles.main.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.softaai.newsapp.R
import com.softaai.newsapp.databinding.ItemArticleBinding
import com.softaai.newsapp.model.Article

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article, onItemClicked: (Article, ImageView) -> Unit) {
        binding.articleDescription.text = article.title
        binding.articleAuthor.text = article.author
        binding.imageView.load(article.urlToImage) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_broken_image)
        }

        binding.root.setOnClickListener {
            onItemClicked(article, binding.imageView)
        }
    }
}