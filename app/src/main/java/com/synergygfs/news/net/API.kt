package com.synergygfs.news.net

import com.google.gson.Gson
import com.synergygfs.news.BuildConfig
import com.synergygfs.news.app.News
import com.synergygfs.news.data.Article
import com.synergygfs.news.data.Articles
import okhttp3.Request
import java.util.*

object API {
    private val gson = Gson()

    private const val baseUrl = "https://newsapi.org/v2/top-headlines"

    fun getArticles(
        language: String? = null,
        topic: String? = null,
        callback: (Vector<Article>) -> Unit
    ) {
        News.getPool()?.submit {
            // Construct the request url
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

            // Construct the request
            val request = Request.Builder()
                .addHeader("Authorization", BuildConfig.NEWS_API_KEY)
                .url(url.toString())
                .build()

            // Send request
            val response = News.getClient()?.newCall(request)!!.execute()

            // Read response
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