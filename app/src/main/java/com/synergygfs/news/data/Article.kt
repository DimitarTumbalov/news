package com.synergygfs.news.data

import java.util.*

data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

data class Articles(val articles: Vector<Article>)