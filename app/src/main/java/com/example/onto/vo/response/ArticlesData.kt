package com.example.onto.vo.response

import com.example.onto.vo.OntoArticle
import com.example.onto.vo.OntoProduct
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesData(val articles: MutableList<OntoArticle>)