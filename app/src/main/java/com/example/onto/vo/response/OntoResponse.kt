package com.example.onto.vo.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoResponse<T>(val data: T)