package com.rudra.moengagenews.util

import com.rudra.moengagenews.data.model.Article
import com.rudra.moengagenews.data.model.News
import com.rudra.moengagenews.data.model.Source
import org.json.JSONObject

fun parseJsonToNews(jsonData: String): News {
        val newsJsonObject = JSONObject(jsonData)
        val status = newsJsonObject.optString("status")
        val articlesArray = newsJsonObject.optJSONArray("articles")

        val articles = mutableListOf<Article>()

        articlesArray?.let { array ->
            for (i in 0 until array.length()) {
                val articleObject = array.getJSONObject(i)
                val sourceObject = articleObject.optJSONObject("source")
                val source = Source(
                    id = sourceObject?.optString("id") ?: "",
                    name = sourceObject?.optString("name") ?: ""
                )
                val article = Article(
                    author = articleObject.optString("author"),
                    content = articleObject.optString("content"),
                    description = articleObject.optString("description"),
                    publishedAt = articleObject.optString("publishedAt"),
                    source = source,
                    title = articleObject.optString("title"),
                    url = articleObject.optString("url"),
                    urlToImage = articleObject.optString("urlToImage")
                )
                articles.add(article)
            }
        }

        return News(articles = articles, status = status)
    }
