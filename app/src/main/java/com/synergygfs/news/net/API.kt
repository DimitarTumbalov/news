package com.synergygfs.news.net

import android.util.Log
import com.google.gson.Gson
import com.synergygfs.news.Constants
import com.synergygfs.news.data.Article
import com.synergygfs.news.data.Articles
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.Executors


object API {
    private val client = OkHttpClient()

    private val pool = Executors.newCachedThreadPool()

    private val gson = Gson()

    private const val baseUrl = "https://newsapi.org/v2"

    fun getArticlesByTopic(topic: String, callback: (Vector<Article>) -> Unit) {
        pool.submit {
            val request = Request.Builder()
                .addHeader("Authorization", Constants.API_KEY)
                .url("$baseUrl/top-headlines?country=bg")
                .build()
            val response = client.newCall(request).execute()

            val jsonString: String = response.body?.string().toString()

            val articles = gson.fromJson(jsonString, Articles::class.java)
            Log.d("test", articles.articles.size.toString())
            callback(articles.articles)
        }
    }
}