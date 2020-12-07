package com.example.onto.vo.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoArticle(
    @field:Json(name = "article_id") val id: Long,
    val name: String,
    val image: String,
    val text: String,
    val date: String
)