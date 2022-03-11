package com.synergygfs.news.net

import com.google.gson.Gson
import com.synergygfs.news.BuildConfig
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

    private const val baseUrl = "https://newsapi.org/v2/top-headlines"

    fun getArticles(
        language: String? = null,
        topic: String? = null,
        callback: (Vector<Article>) -> Unit
    ) {
        pool.submit {
            val url = StringBuilder()
            url.append(baseUrl)

            if (language != null) {
                url.append("?language=$language")

                if (topic != null)
                    url.append("&category=$topic")
            } else if (topic != null) {
                url.append("?category=$topic")
            }

            var articles = Vector<Article>()

            val request = Request.Builder()
                .addHeader("Authorization", BuildConfig.NEWS_API_KEY)
                .url(url.toString())
                .build()

            val response = client.newCall(request).execute()
            response.body!!.charStream().use { reader ->
                try {
                    val articlesData: Articles? = gson.fromJson(reader, Articles::class.java)

                    articlesData?.articles?.let {
                        articles = it
                    }
                } catch (i: Exception) {
                } finally {
                    callback(articles)
                }
            }
        }
    }
}