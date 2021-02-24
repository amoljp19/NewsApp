package com.softaai.newsapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.softaai.newsapp.data.persistence.NewsArticlesDatabase
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.model.Source
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var mDatabase: NewsArticlesDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsArticlesDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_articles() = runBlocking {
        val articles = listOf(
            Article(1, "Test 1", "Test 1", "Test 1", "Test 1", Source("1", "Test 1"), "Test 1", "Test 1", "Test 1"),
            Article(2, "Test 2", "Test 2", "Test 2", "Test 2", Source("2", "Test 2"), "Test 2", "Test 2", "Test 2")
            )

        mDatabase.getArticlesDao().addArticles(articles)

        val dbArticles = mDatabase.getArticlesDao().getAllArticles().first()

        assertThat(dbArticles, equalTo(articles))
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_article_by_id() = runBlocking {
        val articles = listOf(
            Article(1, "Test 1", "Test 1", "Test 1", "Test 1", Source("1", "Test 1"), "Test 1", "Test 1", "Test 1"),
            Article(2, "Test 2", "Test 2", "Test 2", "Test 2", Source("2", "Test 2"), "Test 2", "Test 2", "Test 2")
        )

        mDatabase.getArticlesDao().addArticles(articles)

        var dbArticles = mDatabase.getArticlesDao().getArticleById(1).first()
        assertThat(dbArticles, equalTo(articles[0]))

        dbArticles = mDatabase.getArticlesDao().getArticleById(2).first()
        assertThat(dbArticles, equalTo(articles[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}