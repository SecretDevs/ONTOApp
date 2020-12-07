package com.example.onto.vo.response

import com.example.onto.vo.remote.OntoArticle
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesData(val articles: MutableList<OntoArticle>)