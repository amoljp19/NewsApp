package com.softaai.newsapp.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softaai.newsapp.model.Article
import com.softaai.newsapp.model.Comments
import com.softaai.newsapp.model.Likes

@Database(entities = [Article::class, Likes::class, Comments::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsArticlesDatabase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticlesDao

    companion object {
        const val DB_NAME = "news_articles_database"

        @Volatile
        private var INSTANCE: NewsArticlesDatabase? = null

        fun getInstance(context: Context): NewsArticlesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsArticlesDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}