package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoArticle(
    @Json(name = "article_id") val id: Long,
    val name: String,
    val image: String,
    val text: String,
    val date: String
)